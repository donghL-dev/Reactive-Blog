package com.donghun.reactiveblog.service;

import com.donghun.reactiveblog.config.auth.JwtResolver;
import com.donghun.reactiveblog.domain.Comment;
import com.donghun.reactiveblog.domain.Follow;
import com.donghun.reactiveblog.domain.vo.*;
import com.donghun.reactiveblog.repository.ArticleRepository;
import com.donghun.reactiveblog.repository.CommentRepository;
import com.donghun.reactiveblog.repository.FollowRepository;
import com.donghun.reactiveblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

/**
 * @author donghL-dev
 * @since  2019-12-13
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final ArticleRepository articleRepository;

    private final FollowRepository followRepository;

    private final JwtResolver jwtResolver;

    public Mono<ServerResponse> postCommentProcessLogic(ServerRequest request) {
        return request.bodyToMono(CommentPostVO.class).map(CommentPostVO::getComment)
                .flatMap(commentDTO -> userRepository.findByEmail(jwtResolver.getUserByToken(request))
                        .flatMap(user -> articleRepository.findBySlug(request.pathVariable("slug"))
                                .flatMap(article -> commentRepository.save(Comment.builder()
                                        .id(UUID.randomUUID().toString())
                                        .body(commentDTO.getBody())
                                        .author(new ProfileBodyVO(user, false))
                                        .slug(article.getSlug())
                                        .createdAt(LocalDateTime.now())
                                        .updatedAt(LocalDateTime.now())
                                        .build()))
                                        .flatMap(comment -> ServerResponse.created(request.uri()).contentType(MediaType.APPLICATION_JSON)
                                                .body(Mono.just(new CommentVO(comment)), CommentVO.class))
                                .switchIfEmpty(ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).body(
                                        Mono.just(new ErrorStatusVO(Collections.singletonList("Article does not exist")).getErrors()), Map.class)))
                        .switchIfEmpty(ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).body(
                                Mono.just(new ErrorStatusVO(Collections.singletonList("User does not exist")).getErrors()), Map.class)));
    }

    public Mono<ServerResponse> getCommentsProcessLogic(ServerRequest request) {
        return commentRepository.findBySlug(request.pathVariable("slug"))
                .flatMap(comment -> {
                    String currentUser = request.headers().header("Authorization").size() == 0 ?
                            "Nullable@email.com" : jwtResolver.getUserByToken(request);
                    Mono<Follow> followMono = followRepository.findByFollowAndFollowing(comment.getAuthor().
                            getEmail(), currentUser).switchIfEmpty(Mono.just(Follow.builder().follow("Invalid Follow").build()));

                    return followMono.flatMap(follow -> {
                        if(follow.getFollow().equals("Invalid Follow")) {
                            comment.getAuthor().setFallowing(false);
                        } else {
                            comment.getAuthor().setFallowing(true);
                        }
                        return Mono.just(comment);
                    });
                }).collectList().flatMap(comments ->
                        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                                .body(Mono.just(new CommentsVO(comments)), CommentsVO.class))
                .switchIfEmpty(ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(new CommentsVO(Collections.emptyList())), CommentsVO.class));
    }

    public Mono<ServerResponse> deleteCommentProcessLogic(ServerRequest request) {
        return articleRepository.findBySlug(request.pathVariable("slug"))
                .flatMap(article -> commentRepository.findById(request.pathVariable("id"))
                        .flatMap(comment -> {
                            if(comment.getAuthor().getEmail().equals(jwtResolver.getUserByToken(request))) {
                                return commentRepository.delete(comment).then(ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON).body(Mono.just(new SuccessVO(
                                                new StatusVO())), SuccessVO.class));
                            } else {
                                return ServerResponse.status(401).contentType(MediaType.APPLICATION_JSON).body(
                                        Mono.just(new ErrorStatusVO(Collections.singletonList("You didn't write this article, " +
                                                "only the author can delete the article.")).getErrors()), Map.class);
                            }
                        })
                        .switchIfEmpty(ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).body(
                                Mono.just(new ErrorStatusVO(Collections.singletonList("Comment does not exist")).getErrors()), Map.class)))
                .switchIfEmpty(ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).body(
                        Mono.just(new ErrorStatusVO(Collections.singletonList("Article does not exist")).getErrors()), Map.class));
    }
}
