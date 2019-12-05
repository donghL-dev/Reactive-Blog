package com.donghun.reactiveblog.handler;

import com.donghun.reactiveblog.config.auth.SecurityConstants;
import com.donghun.reactiveblog.domain.Token;
import com.donghun.reactiveblog.domain.User;
import com.donghun.reactiveblog.domain.dto.LoginDTO;
import com.donghun.reactiveblog.domain.dto.SignUpDTO;
import com.donghun.reactiveblog.domain.vo.LoginVO;
import com.donghun.reactiveblog.domain.vo.SignUpVO;
import com.donghun.reactiveblog.repository.TokenRepository;
import com.donghun.reactiveblog.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class UserHandlerTest {

    @Value("${springbootwebfluxjjwt.jjwt.secret}")
    private String secret;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenRepository tokenRepository;

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
                .assertNext(i -> then(i).isNotNull())
                .verifyComplete();
    }

    @Test
    @DisplayName("로그인 API 테스트")
    public void loginRouteTest() {
        User user = User.builder()
                    .id(UUID.randomUUID().toString())
                    .username("test_user")
                    .email("testUser@email.com")
                    .password(passwordEncoder.encode("testPassword1234"))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

        Mono.just(user).flatMap(userRepository::save).subscribe();

        LoginDTO loginDTO = LoginDTO.builder().email(user.getEmail()).password("testPassword1234").build();
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
                .username("test_user")
                .email("testUser@email.com")
                .password(passwordEncoder.encode("testPassword1234"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        String token = generateToken(user);

        Mono.just(Token.builder().id(UUID.randomUUID().toString()).email(user.getEmail()).token(token).build())
                .flatMap(tokenRepository::save).subscribe();

        webTestClient.post()
                .uri("/api/users/logout")
                .header("Authorization", token)
                .exchange()
                .expectStatus()
                .isOk();

        Mono<Token> tokenMono = tokenRepository.findByEmail(user.getEmail())
                .switchIfEmpty(Mono.just(Token.builder().email("a").token("b").build()));

        StepVerifier.create(tokenMono)
                .assertNext(i -> {
                    then(i.getEmail()).isEqualTo("a");
                    then(i.getToken()).isEqualTo("b");
                })
                .verifyComplete();
    }

    public String generateToken(User user) {
        List<String> roles = Stream.of(new SimpleGrantedAuthority("USER")).map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        byte[] signingKey = secret.getBytes();

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject("Perfect-Matching JWT Token")
                .claim("idx", user.getId())
                .claim("email", user.getEmail())
                .claim("userName", user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000000))
                .claim("role", roles)
                .compact();
    }

}