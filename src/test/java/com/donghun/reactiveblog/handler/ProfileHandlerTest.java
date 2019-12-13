package com.donghun.reactiveblog.handler;


import com.donghun.reactiveblog.domain.Follow;
import com.donghun.reactiveblog.domain.Token;
import com.donghun.reactiveblog.domain.User;
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
import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-09
 */
public class ProfileHandlerTest extends BaseHandlerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {
        userRepository.deleteAll().subscribe();
        tokenRepository.deleteAll().subscribe();
    }

    @Test
    @DisplayName("프로필 조회 API 테스트")
    public void getProfileRouteTest() {
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("test_user6")
                .email("test_user6@email.com")
                .password(passwordEncoder.encode("testPassword123445"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mono.just(user).flatMap(userRepository::save).subscribe();

        String token = generateToken(user);
        Mono.just(Token.builder().id(UUID.randomUUID().toString()).email(user.getEmail()).token(token).build())
                .flatMap(tokenRepository::save).subscribe();

        webTestClient.get()
                .uri("/api/profile/" + user.getUsername())
                .header(HttpHeaders.AUTHORIZATION, token)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("프로필 팔로잉 API 테스트")
    public void followUserRouteTest() {
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("test_user7")
                .email("test_user7@email.com")
                .password(passwordEncoder.encode("testPassword123445"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User user2 = User.builder()
                .id(UUID.randomUUID().toString())
                .username("test_user8")
                .email("test_user8@email.com")
                .password(passwordEncoder.encode("testPassword123445"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mono.just(user).flatMap(userRepository::save).subscribe();
        Mono.just(user2).flatMap(userRepository::save).subscribe();

        String token = generateToken(user);
        Mono.just(Token.builder().id(UUID.randomUUID().toString()).email(user.getEmail()).token(token).build())
                .flatMap(tokenRepository::save).subscribe();

        webTestClient.post()
                .uri("/api/profile/" + user2.getUsername() + "/follow")
                .header(HttpHeaders.AUTHORIZATION, token)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isCreated();

        Mono<Follow> followMono = followRepository.findByFollowAndFollowing(user2.getEmail(), user.getEmail());

        StepVerifier.create(followMono)
                .assertNext(i -> {
                    then(i).isNotNull();
                    then(i.getFollow()).isEqualTo(user2.getEmail());
                    then(i.getFollowing()).isEqualTo(user.getEmail());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("프로필 언 팔로잉 API 테스트")
    public void unFollowUserRouteTest() {
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("test_user9")
                .email("test_user9@email.com")
                .password(passwordEncoder.encode("testPassword123445"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User user2 = User.builder()
                .id(UUID.randomUUID().toString())
                .username("test_user10")
                .email("test_user10@email.com")
                .password(passwordEncoder.encode("testPassword123445"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mono.just(user).flatMap(userRepository::save).subscribe();
        Mono.just(user2).flatMap(userRepository::save).subscribe();

        Follow follow = Follow.builder().id(UUID.randomUUID().toString()).follow(user2.getEmail())
                .following(user.getEmail()).build();

       Mono.just(follow).flatMap(followRepository::save).subscribe();

        String token = generateToken(user);
        Mono.just(Token.builder().id(UUID.randomUUID().toString()).email(user.getEmail()).token(token).build())
                .flatMap(tokenRepository::save).subscribe();

        webTestClient.delete()
                .uri("/api/profile/" + user2.getUsername() + "/follow")
                .header(HttpHeaders.AUTHORIZATION, token)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isOk();

        Mono<Follow> followMono = followRepository.findByFollowAndFollowing(user2.getEmail(), user.getEmail())
                                        .switchIfEmpty(Mono.just(Follow.builder().follow("success_test").build()));

        StepVerifier.create(followMono)
                .assertNext(i ->
                    then(i.getFollow()).isEqualTo("success_test")
                )
                .verifyComplete();

    }

}