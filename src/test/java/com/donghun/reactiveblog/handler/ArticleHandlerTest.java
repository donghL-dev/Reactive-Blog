package com.donghun.reactiveblog.handler;

import com.donghun.reactiveblog.domain.Article;
import com.donghun.reactiveblog.domain.Token;
import com.donghun.reactiveblog.domain.User;
import com.donghun.reactiveblog.domain.dto.ArticlePostDTO;
import com.donghun.reactiveblog.domain.dto.ArticlePutDTO;
import com.donghun.reactiveblog.domain.vo.ArticlePostVO;
import com.donghun.reactiveblog.domain.vo.ArticlePutVO;
import com.donghun.reactiveblog.domain.vo.ProfileBodyVO;
import com.donghun.reactiveblog.repository.ArticleRepository;
import com.donghun.reactiveblog.repository.FollowRepository;
import com.donghun.reactiveblog.repository.TokenRepository;
import com.donghun.reactiveblog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-12
 */
class ArticleHandlerTest extends BaseHandlerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {
        userRepository.deleteAll();
        tokenRepository.deleteAll();
        articleRepository.deleteAll();

    }

    @Test
    @DisplayName("Article 리스트를 불러오는 API 테스트")
    public void getArticlesRouteTest() {
        webTestClient.get()
                .uri("/api/articles")
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("Article 피드 리스트를 불러오는 API 테스트")
    public void getFeedArticleRouteTest() {

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("test_user12")
                .email("test_user12@email.com")
                .password(passwordEncoder.encode("testPassword123445"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mono.just(user).flatMap(userRepository::save).subscribe();

        String token = generateToken(user);
        Mono.just(Token.builder().id(UUID.randomUUID().toString()).email(user.getEmail()).token(token).build())
                .flatMap(tokenRepository::save).subscribe();

        webTestClient.get()
                .uri("/api/articles")
                .header(HttpHeaders.AUTHORIZATION, token)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("단일 Article 을 조회하는 API 테스트")
    public void getArticleRouteTest() {
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("test_user13")
                .email("test_user13@email.com")
                .password(passwordEncoder.encode("testPassword123445"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mono.just(user).flatMap(userRepository::save).subscribe();

        String token = generateToken(user);
        Mono.just(Token.builder().id(UUID.randomUUID().toString()).email(user.getEmail()).token(token).build())
                .flatMap(tokenRepository::save).subscribe();

        Article article = Article.builder()
                .id(UUID.randomUUID().toString())
                .slug("Hello-World-1123")
                .title("Hello World 1123")
                .description("it is Hello World")
                .body("Hello World Content")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .favorites(Collections.emptySet())
                .tags(Collections.emptySet())
                .favoritesCount(0)
                .author(new ProfileBodyVO(User.builder().image("image").bio("bio").username("test_user12")
                        .email("test_user12@email.com").build(), false))
                .build();

        Mono.just(article).flatMap(articleRepository::save).subscribe();

        webTestClient.get()
                .uri("/api/articles/" + article.getSlug())
                .header(HttpHeaders.AUTHORIZATION, token)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("Article 을 생성하는 요청 API 테스트")
    public void postArticleRouteTest() {

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("test_user14")
                .email("test_user14@email.com")
                .password(passwordEncoder.encode("testPassword123445"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mono.just(user).flatMap(userRepository::save).subscribe();

        String token = generateToken(user);
        Mono.just(Token.builder().id(UUID.randomUUID().toString()).email(user.getEmail()).token(token).build())
                .flatMap(tokenRepository::save).subscribe();

        ArticlePostDTO articlePostDTO = ArticlePostDTO.builder()
                .title("Article Create API Test")
                .description("Article Create API Test")
                .body("테스트 본문 입니다.")
                .tagList(Collections.singleton("react JS"))
                .build();

        ArticlePostVO articlePostVO = new ArticlePostVO();
        articlePostVO.setArticle(articlePostDTO);

        webTestClient.post()
                .uri("/api/articles")
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(Mono.just(articlePostVO), ArticlePostVO.class)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isCreated();

        Mono<Article> articleMono = articleRepository.findBySlug(articlePostDTO.getTitle().replace(" ", "-"));

        StepVerifier.create(articleMono)
                .assertNext(article -> {
                    then(article).isNotNull();
                    then(article.getTitle()).isEqualTo(articlePostDTO.getTitle());
                    then(article.getDescription()).isEqualTo(articlePostDTO.getDescription());
                    then(article.getBody()).isEqualTo(articlePostDTO.getBody());
                }).verifyComplete();
    }

    @Test
    @DisplayName("Article 을 생성한 뒤 수정하는 요청 API 테스트")
    public void putArticleRouteTest() {

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("test_user15")
                .email("test_user15@email.com")
                .password(passwordEncoder.encode("testPassword123445"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mono.just(user).flatMap(userRepository::save).subscribe();

        String token = generateToken(user);
        Mono.just(Token.builder().id(UUID.randomUUID().toString()).email(user.getEmail()).token(token).build())
                .flatMap(tokenRepository::save).subscribe();

        ArticlePostDTO articlePostDTO = ArticlePostDTO.builder()
                .title("Article Create API Test2")
                .description("Article Create API Test2")
                .body("테스트 본문 입니다.")
                .tagList(Collections.singleton("react JS"))
                .build();

        ArticlePostVO articlePostVO = new ArticlePostVO();
        articlePostVO.setArticle(articlePostDTO);

        webTestClient.post()
                .uri("/api/articles")
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(Mono.just(articlePostVO), ArticlePostVO.class)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isCreated();

        ArticlePutDTO articlePutDTO = ArticlePutDTO.builder()
                .title("Article Modify API Test3")
                .description("Article Modify API Test3")
                .body("Article 수정하는 테스트입니다.")
                .build();

        ArticlePutVO articlePutVO = new ArticlePutVO();
        articlePutVO.setArticle(articlePutDTO);

        webTestClient.put()
                .uri("/api/articles/" + articlePostDTO
                        .getTitle().replace(" ", "-"))
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(Mono.just(articlePutVO), ArticlePutVO.class)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isOk();

        Mono<Article> articleMono = articleRepository.findBySlug(articlePutDTO.getTitle().replace(" ", "-"));

        StepVerifier.create(articleMono)
                .assertNext(article -> {
                    then(article).isNotNull();
                    then(article.getTitle()).isEqualTo(articlePutDTO.getTitle());
                    then(article.getDescription()).isEqualTo(articlePutDTO.getDescription());
                    then(article.getBody()).isEqualTo(articlePutDTO.getBody());
                }).verifyComplete();

    }

    @Test
    @DisplayName("Article 을 삭제 하는 API 테스트")
    public void deleteArticleRouteTest() {
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("test_user16")
                .email("test_user16@email.com")
                .password(passwordEncoder.encode("testPassword123445"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mono.just(user).flatMap(userRepository::save).subscribe();

        String token = generateToken(user);
        Mono.just(Token.builder().id(UUID.randomUUID().toString()).email(user.getEmail()).token(token).build())
                .flatMap(tokenRepository::save).subscribe();

        ArticlePostDTO articlePostDTO = ArticlePostDTO.builder()
                .title("Article Create API Test4")
                .description("Article Create API Test4")
                .body("테스트 본문 입니다.")
                .tagList(Collections.singleton("react JS"))
                .build();

        ArticlePostVO articlePostVO = new ArticlePostVO();
        articlePostVO.setArticle(articlePostDTO);

        webTestClient.post()
                .uri("/api/articles")
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(Mono.just(articlePostVO), ArticlePostVO.class)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isCreated();

        webTestClient.delete()
                .uri("/api/articles/" + articlePostDTO.getTitle().replace(" ", "-"))
                .header(HttpHeaders.AUTHORIZATION, token)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isOk();

        Mono<Article> articleMono = articleRepository.findBySlug(articlePostDTO.getTitle().replace(" ", "-"))
                .switchIfEmpty(Mono.just(Article.builder().title("Success Test").build()));

        StepVerifier.create(articleMono)
                .assertNext(article ->
                        then(article.getTitle()).isEqualTo("Success Test"))
                .verifyComplete();

    }




}