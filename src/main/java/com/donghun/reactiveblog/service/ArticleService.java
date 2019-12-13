package com.donghun.reactiveblog.service;

import com.donghun.reactiveblog.config.auth.JwtResolver;
import com.donghun.reactiveblog.domain.Article;
import com.donghun.reactiveblog.domain.Follow;
import com.donghun.reactiveblog.domain.Tag;
import com.donghun.reactiveblog.domain.vo.*;
import com.donghun.reactiveblog.repository.ArticleRepository;
import com.donghun.reactiveblog.repository.FollowRepository;
import com.donghun.reactiveblog.repository.TagRepository;
import com.donghun.reactiveblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author donghL-dev
 * @since  2019-12-10
 */
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final FollowRepository followRepository;

    private final UserRepository userRepository;

    private final TagRepository tagRepository;

    private final Validator validator;

    private final JwtResolver jwtResolver;

    public Mono<ServerResponse> getArticlesProcessLogic(ServerRequest request) {

        return articleRepository.findAll().collectList().flatMap(articles -> {
            Integer count = articles.size();
            List<Article> list = listGenerate(articles, request);

            if(request.headers().header("Authorization").size() == 0) {
                list.forEach(article -> article.getAuthor().setFallowing(false));
                List<ArticleVO> articleVOList = list.stream().map(article -> new ArticleVO(article, "Invalid Email"))
                        .collect(Collectors.toList());
                return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(new ArticlesVO(articleVOList, count)), ArticlesVO.class);
            } else {
                return articleRepository.findAll().flatMap(article -> {
                    Mono<Follow> followMono = followRepository.findByFollowAndFollowing(article.getAuthor().getEmail(), jwtResolver.getUserByToken(request))
                            .switchIfEmpty(Mono.just(Follow.builder().follow("Invalid Value").build()));

                    return followMono.flatMap(follow -> {
                        if(follow.getFollow().equals("Invalid Value")) {
                            article.getAuthor().setFallowing(false);
                        } else {
                            article.getAuthor().setFallowing(true);
                        }
                        return Mono.just(article);
                    });
                }).collectList().flatMap(articleList -> {
                    List<ArticleVO> articleVOList = listGenerate(articleList, request).stream()
                            .map(ar -> new ArticleVO(ar, jwtResolver.getUserByToken(request))).collect(Collectors.toList());
                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(new ArticlesVO(articleVOList, count)), ArticlesVO.class);
                });
            }
        }).switchIfEmpty(ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(new ArticlesVO(Collections.emptyList(), 0)), ArticlesVO.class));
    }

    public Mono<ServerResponse> getFeedArticlesProcessLogic(ServerRequest request) {
        return followRepository.findByFollowing(jwtResolver.getUserByToken(request))
                .collectList().flatMap(follows ->
                        articleRepository.findAll()
                            .filter(article -> article.getAuthor().getEmail().equals(jwtResolver.getUserByToken(request))
                                    || follows.stream().anyMatch(follow ->  follow.getFollow().equals(article.getAuthor().getEmail())))
                            .flatMap(article -> {
                                article.getAuthor().setFallowing(true);
                                return Mono.just(article);
                            }).collectList()
                            .flatMap(articleList -> {
                                List<ArticleVO> articleVOList = listGenerate(articleList, request).stream()
                                    .map(ar -> new ArticleVO(ar, jwtResolver.getUserByToken(request))).collect(Collectors.toList());
                                return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                                    .body(Mono.just(new ArticlesVO(articleVOList, articleList.size())), ArticlesVO.class);
                            })
                            .switchIfEmpty(ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                                .body(Mono.just(new ArticlesVO(Collections.emptyList(), 0)), ArticlesVO.class)))
                .switchIfEmpty(ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(new ArticlesVO(Collections.emptyList(), 0)), ArticlesVO.class));
    }

    public Mono<ServerResponse> getArticleProcessLogic(ServerRequest request) {
        return articleRepository.findBySlug(request.pathVariable("slug"))
                .flatMap(article -> {
                    Mono<Follow> followMono = followRepository.findByFollowAndFollowing(article.getAuthor().getEmail(),
                            jwtResolver.getUserByToken(request))
                            .switchIfEmpty(Mono.just(Follow.builder().follow("Invalid value").build()));

                   return followMono.flatMap(follow -> {
                        if(follow.getFollow().equals("Invalid value")) {
                            article.getAuthor().setFallowing(false);
                        } else {
                            article.getAuthor().setFallowing(true);
                        }
                        return Mono.just(article);
                    });
                }).flatMap(article -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                            .body(Mono.just(new SingleArticleVO(new ArticleVO(article, jwtResolver.getUserByToken(request)))), SingleArticleVO.class))
                .switchIfEmpty(ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(new ErrorStatusVO(Collections.singletonList("Article does not exist")).getErrors()), Map.class));
    }

    public Mono<ServerResponse> postArticleProcessLogic(ServerRequest request) {
        return request.bodyToMono(ArticlePostVO.class)
                .map(ArticlePostVO::getArticle)
                .flatMap(articlePostDTO -> {
                    if(validator.validate(articlePostDTO).isEmpty()) {
                        return userRepository.findByEmail(jwtResolver.getUserByToken(request))
                                .flatMap(user ->  articleRepository.save(Article.builder()
                                        .id(UUID.randomUUID().toString())
                                        .slug(articlePostDTO.getTitle().replace(" ", "-"))
                                        .title(articlePostDTO.getTitle())
                                        .description(articlePostDTO.getDescription())
                                        .body(articlePostDTO.getBody())
                                        .createdAt(LocalDateTime.now())
                                        .updatedAt(LocalDateTime.now())
                                        .favorites(Collections.emptySet())
                                        .author(new ProfileBodyVO(user, false))
                                        .favoritesCount(0)
                                        .tags(articlePostDTO.getTagList()).build())
                                        .flatMap(article -> {
                                            articlePostDTO.getTagList().forEach(s -> tagRepository.findByName(s).switchIfEmpty(
                                                    tagRepository.save(Tag.builder().id(UUID.randomUUID().toString()).name(s).build())).subscribe());
                                            return ServerResponse.created(request.uri()).contentType(MediaType.APPLICATION_JSON)
                                                    .body(Mono.just(new SingleArticleVO(new ArticleVO(article, jwtResolver.getUserByToken(request)))), SingleArticleVO.class);
                                        }))
                                .switchIfEmpty(ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).body(
                                        Mono.just(new ErrorStatusVO(Collections.singletonList("User does not exist")).getErrors()), Map.class));
                    } else {
                        return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON)
                                .body(Mono.just(new ErrorStatusVO(validation(articlePostDTO)).getErrors()), Map.class);
                    }
                });
    }

    public Mono<ServerResponse> putArticleProcessLogic(ServerRequest request) {
        return request.bodyToMono(ArticlePutVO.class).map(ArticlePutVO::getArticle)
                .flatMap(articlePutDTO -> {
                    if(validator.validate(articlePutDTO).isEmpty()) {
                        return userRepository.findByEmail(jwtResolver.getUserByToken(request))
                                .flatMap(user -> articleRepository.findBySlug(request.pathVariable("slug"))
                                        .flatMap(article -> articleRepository.save(Article.builder()
                                                .id(article.getId())
                                                .slug(articlePutDTO.getTitle() == null
                                                        ? article.getSlug() : articlePutDTO.getTitle().replace(" ", "-"))
                                                .title(articlePutDTO.getTitle() == null ? article.getTitle() : articlePutDTO.getTitle())
                                                .body(articlePutDTO.getBody() == null ? article.getBody() : articlePutDTO.getBody())
                                                .tags(articlePutDTO.getTagList() == null ? article.getTags() : articlePutDTO.getTagList())
                                                .favoritesCount(article.getFavoritesCount())
                                                .author(article.getAuthor())
                                                .favorites(article.getFavorites())
                                                .description(articlePutDTO.getDescription() == null
                                                        ? article.getDescription() : articlePutDTO.getDescription())
                                                .updatedAt(LocalDateTime.now())
                                                .createdAt(article.getCreatedAt())
                                                .build())
                                                .flatMap(article1 ->
                                                        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                                                            .body(Mono.just(new SingleArticleVO(new ArticleVO(article1,
                                                                    jwtResolver.getUserByToken(request)))), SingleArticleVO.class)))
                                        .switchIfEmpty(ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).body(
                                                Mono.just(new ErrorStatusVO(Collections.singletonList("Article does not exist")).getErrors()), Map.class))
                                .switchIfEmpty(ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).body(
                                        Mono.just(new ErrorStatusVO(Collections.singletonList("User does not exist")).getErrors()), Map.class)));
                    } else {
                        return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON)
                                .body(Mono.just(new ErrorStatusVO(validation(articlePutDTO)).getErrors()), Map.class);
                    }
                });
    }

    public Mono<ServerResponse> deleteArticleProcessLogic(ServerRequest request) {
        return articleRepository.findBySlug(request.pathVariable("slug"))
                .flatMap(article -> {
                    if(article.getAuthor().getEmail().equals(jwtResolver.getUserByToken(request))) {
                        return articleRepository.delete(article).then(ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono
                                .just(new SuccessVO(new StatusVO())), SuccessVO.class));
                    } else {
                        return ServerResponse.status(401).contentType(MediaType.APPLICATION_JSON).body(
                                Mono.just(new ErrorStatusVO(Collections.singletonList("You didn't write this article, " +
                                        "only the author can delete the article.")).getErrors()), Map.class);
                    }
                })
                .switchIfEmpty(ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).body(
                        Mono.just(new ErrorStatusVO(Collections.singletonList("Article does not exist")).getErrors()), Map.class));
    }

    private List<Article> listGenerate(List<Article> articles, ServerRequest request) {
        return articles.stream()
                .filter(article -> !request.queryParam("tag").isPresent() ||
                        article.getTags().contains(request.queryParam("tag").get()))
                .filter(article -> !request.queryParam("author").isPresent() ||
                        article.getAuthor().getUsername().equals(request.queryParam("author").get()))
                .filter(article -> !request.queryParam("favorited").isPresent() ||
                        article.getFavorites().contains(request.queryParam("favorited").get()))
                .sorted(Comparator.comparing(Article::getCreatedAt).reversed())
                .limit(request.queryParam("limit").isPresent() ? Integer.parseInt(request.queryParam("limit").get()) : 20)
                .skip(request.queryParam("offset").isPresent() ? Integer.parseInt(request.queryParam("offset").get()) : 0)
                .collect(Collectors.toList());
    }

    public<T> List<String> validation(T object) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
        return constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
    }

}
