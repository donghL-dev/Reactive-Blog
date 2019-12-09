package com.donghun.reactiveblog.handler;

import com.donghun.reactiveblog.config.auth.SecurityConstants;
import com.donghun.reactiveblog.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author donghL-dev
 * @since  2019-12-09
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class BaseHandlerTest {

    @Value("${springbootwebfluxjjwt.jjwt.secret}")
    private String secret;

    @Autowired
    public WebTestClient webTestClient;

    public String generateToken(User user) {
        List<String> roles = Stream.of(new SimpleGrantedAuthority("USER")).map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        byte[] signingKey = secret.getBytes();

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject("Reactive-Blog JWT Token")
                .claim("idx", user.getId())
                .claim("email", user.getEmail())
                .claim("userName", user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000000))
                .claim("role", roles)
                .compact();
    }
}
