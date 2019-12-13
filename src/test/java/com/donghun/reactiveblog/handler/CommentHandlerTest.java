package com.donghun.reactiveblog.handler;

import com.donghun.reactiveblog.domain.Article;
import com.donghun.reactiveblog.domain.Comment;
import com.donghun.reactiveblog.domain.Token;
import com.donghun.reactiveblog.domain.User;
import com.donghun.reactiveblog.domain.dto.CommentDTO;
import com.donghun.reactiveblog.domain.vo.CommentPostVO;
import com.donghun.reactiveblog.domain.vo.ProfileBodyVO;
import com.donghun.reactiveblog.repository.ArticleRepository;
import com.donghun.reactiveblog.repository.CommentRepository;
import com.donghun.reactiveblog.repository.TokenRepository;
import com.donghun.reactiveblog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-13
 */
class CommentHandlerTest extends BaseHandlerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {
        userRepository.deleteAll().subscribe();
        tokenRepository.deleteAll().subscribe();
        articleRepository.deleteAll().subscribe();
        commentRepository.deleteAll().subscribe();
    }

    @Test
    @DisplayName("Comment 를 생성하는 API 테스트")
    public void postCommentRouteTest() {

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

        Article article = Article.builder()
                .id(UUID.randomUUID().toString())
                .slug("Create-Comment-Post-Test")
                .title("Create Comment Post Test")
                .description("it is Hello World ")
                .body("Hello World Content ")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .favorites(Collections.emptySet())
                .tags(Collections.emptySet())
                .favoritesCount(0)
                .author(new ProfileBodyVO(user, false))
                .build();

        Mono.just(article).flatMap(articleRepository::save).subscribe();

        CommentDTO commentDTO = CommentDTO.builder().body("테스트 내용1").build();
        CommentPostVO commentPostVO = new CommentPostVO();
        commentPostVO.setComment(commentDTO);

        webTestClient.post()
                .uri("/api/articles/" + article.getSlug() + "/comments")
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(Mono.just(commentPostVO), CommentPostVO.class)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isCreated();

        Flux<Comment> commentFlux = commentRepository.findBySlug(article.getSlug());

        StepVerifier.create(commentFlux)
                .assertNext(comment -> then(comment.getBody()).isEqualTo(commentDTO.getBody()))
                .verifyComplete();
    }

    @Test
    @DisplayName("Comment 를 조회하는 API 테스트")
    public void getCommentsRouteTest() {
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

        Article article = Article.builder()
                .id(UUID.randomUUID().toString())
                .slug("Create-Comment-Post-Test")
                .title("Create Comment Post Test")
                .description("it is Hello World ")
                .body("Hello World Content ")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .favorites(Collections.emptySet())
                .tags(Collections.emptySet())
                .favoritesCount(0)
                .author(new ProfileBodyVO(user, false))
                .build();

        Mono.just(article).flatMap(articleRepository::save).subscribe();

        webTestClient.get()
                .uri("/api/articles/" + article.getSlug() + "/comments")
                .header(HttpHeaders.AUTHORIZATION, token)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("Comment를 삭제하는 API 테스트")
    public void deleteCommentRouteTest() {
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

        Article article = Article.builder()
                .id(UUID.randomUUID().toString())
                .slug("Create-Comment-Post-Test6")
                .title("Create Comment Post Test6")
                .description("it is Hello World ")
                .body("Hello World Content ")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .favorites(Collections.emptySet())
                .tags(Collections.emptySet())
                .favoritesCount(0)
                .author(new ProfileBodyVO(user, false))
                .build();

        Mono.just(article).flatMap(articleRepository::save).subscribe();

        Comment comment = Comment.builder()
                .id(UUID.randomUUID().toString())
                .body("곧 삭제될 댓글")
                .author(new ProfileBodyVO(user, false))
                .slug(article.getSlug())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();

        Mono.just(comment).flatMap(commentRepository::save).subscribe();

        webTestClient.delete()
                .uri("/api/articles/" + article.getSlug() + "/comments/" + comment.getId())
                .header(HttpHeaders.AUTHORIZATION, token)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isOk();
    }

}