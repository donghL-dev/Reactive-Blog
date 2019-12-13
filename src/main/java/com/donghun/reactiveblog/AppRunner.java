package com.donghun.reactiveblog;

import com.donghun.reactiveblog.domain.Article;
import com.donghun.reactiveblog.domain.User;
import com.donghun.reactiveblog.domain.vo.ProfileBodyVO;
import com.donghun.reactiveblog.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * @author donghL-dev
 * @since  2019-12-02
 */
@Component
@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final FollowRepository followRepository;

    private final ArticleRepository articleRepository;

    private final CommentRepository commentRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll().thenMany(
                Flux.fromStream(IntStream.rangeClosed(1, 10).mapToObj(this::createUser)
                ).flatMap(userRepository::save)
        ).subscribe();

        articleRepository.deleteAll().thenMany(
                Flux.fromStream(IntStream.rangeClosed(1, 30).mapToObj(this::createArticle))
                        .flatMap(articleRepository::save)).subscribe();


        tokenRepository.deleteAll().subscribe();
        followRepository.deleteAll().subscribe();
        commentRepository.deleteAll().subscribe();
    }

    public User createUser(int index) {
        return User.builder()
                .id(UUID.randomUUID().toString())
                .username("user" + index)
                .email("user" + index + "@email.com")
                .password(passwordEncoder.encode("12345"))
                .bio("저는 USER "+ index  +"입니다.")
                .createdAt(LocalDateTime.now())
                .image("")
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public Article createArticle(int index) {
        Random random = new Random();
        int rndNumber = random.nextInt(10) + 1;
        return Article.builder()
                .id(UUID.randomUUID().toString())
                .slug("Hello-World-" + index)
                .title("Hello World " + index)
                .description("it is Hello World " + index)
                .body("Hello World Content " + index)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .favorites(Collections.emptySet())
                .tags(Collections.emptySet())
                .favoritesCount(0)
                .author(new ProfileBodyVO(User.builder().image("image").bio("bio").username("user" + rndNumber)
                        .email("user" + rndNumber + "@email.com").build(), false))
                .build();
    }

}
