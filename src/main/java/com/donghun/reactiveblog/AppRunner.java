package com.donghun.reactiveblog;

import com.donghun.reactiveblog.domain.User;
import com.donghun.reactiveblog.repository.FollowRepository;
import com.donghun.reactiveblog.repository.TokenRepository;
import com.donghun.reactiveblog.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author donghL-dev
 * @since  2019-12-02
 */
@Component
public class AppRunner implements CommandLineRunner {

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final FollowRepository followRepository;

    public AppRunner(UserRepository userRepository, TokenRepository tokenRepository, FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.followRepository = followRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll().thenMany(
                Mono.just(User.builder()
                        .id(UUID.randomUUID().toString())
                        .username("User1")
                        .email("User1@email.com")
                        .password("12345")
                        .bio("저는 USER 1입니다.")
                        .createdAt(LocalDateTime.now())
                        .image("")
                        .updatedAt(LocalDateTime.now())
                        .build()
                ).flatMap(userRepository::save)
        ).subscribe();

        tokenRepository.deleteAll().subscribe();
        followRepository.deleteAll().subscribe();
    }
}
