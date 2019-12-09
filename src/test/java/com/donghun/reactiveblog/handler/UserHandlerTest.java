package com.donghun.reactiveblog.handler;

import com.donghun.reactiveblog.config.auth.SecurityConstants;
import com.donghun.reactiveblog.domain.Token;
import com.donghun.reactiveblog.domain.User;
import com.donghun.reactiveblog.domain.dto.LoginDTO;
import com.donghun.reactiveblog.domain.dto.SignUpDTO;
import com.donghun.reactiveblog.domain.dto.UserDTO;
import com.donghun.reactiveblog.domain.vo.CurrentUserVO;
import com.donghun.reactiveblog.domain.vo.LoginVO;
import com.donghun.reactiveblog.domain.vo.SignUpVO;
import com.donghun.reactiveblog.repository.TokenRepository;
import com.donghun.reactiveblog.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-05
 */
public class UserHandlerTest extends BaseHandlerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenRepository tokenRepository;

    @BeforeEach
    public void init() {
        userRepository.deleteAll();
        tokenRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 가입 API 테스트")
    public void signUpRouteTest() {
        SignUpDTO signUpDTO = SignUpDTO.builder()
                                .username("test_user")
                                .email("test_user@email.com")
                                .password("testpassword1234@#$").build();
        SignUpVO signUpVO = new SignUpVO();
        signUpVO.setUser(signUpDTO);

        webTestClient.post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(signUpVO), SignUpVO.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);

        Mono<User> userMono = userRepository.findByEmail(signUpDTO.getEmail());

        StepVerifier.create(userMono)
                .assertNext(i -> {
                    then(i.getUsername()).isEqualTo("test_user");
                    then(i.getEmail()).isEqualTo("test_user@email.com");
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("로그인 API 테스트")
    public void loginRouteTest() {
        User user = User.builder()
                    .id(UUID.randomUUID().toString())
                    .username("test_user22")
                    .email("test_user22@email.com")
                    .password(passwordEncoder.encode("testPassword1234"))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

        Mono.just(user).flatMap(userRepository::save).subscribe();

        LoginDTO loginDTO = LoginDTO.builder().email("test_user22@email.com").password("testPassword1234").build();
        LoginVO loginVO = new LoginVO();
        loginVO.setUser(loginDTO);

        webTestClient.post()
                .uri("/api/users/login")
                .body(Mono.just(loginVO), LoginVO.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @DisplayName("로그아웃 API 테스트")
    public void logoutRouteTest() {
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("test_user33")
                .email("testUser33@email.com")
                .password(passwordEncoder.encode("testPassword1234"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        String token = generateToken(user);

        Mono.just(Token.builder().id(UUID.randomUUID().toString()).email(user.getEmail()).token(token).build())
                .flatMap(tokenRepository::save).subscribe();

        webTestClient.post()
                .uri("/api/users/logout")
                .header(HttpHeaders.AUTHORIZATION, token)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isOk();

    }

    @Test
    @DisplayName("현재 유저 정보를 가져오는 API 테스트")
    public void currentUserRouteTest() {
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("test_user44")
                .email("test_user44@email.com")
                .password(passwordEncoder.encode("testPassword123445"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mono.just(user).flatMap(userRepository::save).subscribe();

        String token = generateToken(user);
        Mono.just(Token.builder().id(UUID.randomUUID().toString()).email(user.getEmail()).token(token).build())
                .flatMap(tokenRepository::save).subscribe();

        webTestClient.get()
                .uri("/api/user")
                .header(HttpHeaders.AUTHORIZATION, token)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("유저 정보를 업데이트 하는 API 테스트")
    public void updateUserRouteTest() {
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("test_user55")
                .email("test_user55@email.com")
                .password(passwordEncoder.encode("testPassword1234"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .bio("")
                .token("")
                .image("")
                .build();

        Mono.just(user).flatMap(userRepository::save).subscribe();

        String token = generateToken(user);
        Mono.just(Token.builder().id(UUID.randomUUID().toString()).email(user.getEmail()).token(token).build())
                .flatMap(tokenRepository::save).subscribe();

        UserDTO userDTO = UserDTO.builder()
                        .bio("이런 사람입니다.")
                        .email("me@donghun.dev")
                        .image("https://image.com")
                        .build();

        CurrentUserVO currentUserVO = new CurrentUserVO();
        currentUserVO.setUser(userDTO);

        webTestClient.put()
                .uri("/api/user")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(currentUserVO), CurrentUserVO.class)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isOk();

        Mono<User> userMono = userRepository.findById(user.getId());

        StepVerifier.create(userMono)
                .assertNext(i -> {
                    then(i.getBio()).isEqualTo(userDTO.getBio());
                    then(i.getEmail()).isEqualTo(userDTO.getEmail());
                    then(i.getImage()).isEqualTo(userDTO.getImage());
                })
                .verifyComplete();
    }

}