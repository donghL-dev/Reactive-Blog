package com.donghun.reactiveblog;

import com.donghun.reactiveblog.domain.User;
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

    public AppRunner(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    }
}
