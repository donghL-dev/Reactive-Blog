package com.donghun.reactiveblog.service;

import com.donghun.reactiveblog.config.auth.JwtResolver;
import com.donghun.reactiveblog.config.auth.SecurityConstants;
import com.donghun.reactiveblog.domain.Token;
import com.donghun.reactiveblog.domain.User;
import com.donghun.reactiveblog.domain.vo.*;
import com.donghun.reactiveblog.repository.TokenRepository;
import com.donghun.reactiveblog.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author donghL-dev
 * @since  2019-12-04
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final Validator validator;

    private final JwtResolver jwtResolver;

    @Value("${springbootwebfluxjjwt.jjwt.secret}")
    private String secret;

    @Value("${springbootwebfluxjjwt.jjwt.expiration}")
    private String expirationTime;

    public Mono<ServerResponse> signUpProcessLogic(ServerRequest request) {
        return request.bodyToMono(SignUpVO.class)
                .map(SignUpVO::getUser)
                .flatMap(user -> {
                    if(validator.validate(user).isEmpty()) {
                        return userRepository.findByUsername(user.getUsername())
                                .flatMap(existUser -> ServerResponse.badRequest().body(Mono.just(
                                        new ErrorStatusVO(Collections.singletonList("Username already exist")).getErrors()), Map.class))
                                .switchIfEmpty(userRepository.findByEmail(user.getEmail())
                                        .flatMap(dbUser -> ServerResponse.badRequest().body(Mono.just(
                                                new ErrorStatusVO(Collections.singletonList("Email already exist")).getErrors()), Map.class))
                                        .switchIfEmpty(userRepository.save(new User().createUser(user, passwordEncoder))
                                                .flatMap(savedUser -> ServerResponse.created(URI.create("/aoi/users"))
                                                        .contentType(MediaType.APPLICATION_JSON).body(Mono.just(new UserVO(savedUser)), UserVO.class))));

                    } else {
                        return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).body(
                                Mono.just(new ErrorStatusVO(validation(user)).getErrors()), Map.class);
                    }
                });
    }


    public Mono<ServerResponse> loginProcessLogic(ServerRequest request) {
        return request.bodyToMono(LoginVO.class).map(LoginVO::getUser)
                .flatMap(loginDTO ->  {
                    if(validator.validate(loginDTO).isEmpty()) {
                        return userRepository.findByEmail(loginDTO.getEmail())
                                .flatMap(user -> {
                                    if(passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
                                        generateToken(user);
                                        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                                                .body(Mono.just(new UserVO(user)), UserVO.class);
                                    } else {
                                        return ServerResponse.badRequest().body(
                                                Mono.just(new ErrorStatusVO(Collections.singletonList("Invalid credentials")).getErrors()), Map.class);
                                    }
                                }).switchIfEmpty(ServerResponse.badRequest().body(
                                        Mono.just(new ErrorStatusVO(Collections.singletonList("User does not exist")).getErrors()), Map.class));
                    } else {
                        return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).body(
                                Mono.just(new ErrorStatusVO(validation(loginDTO)).getErrors()), Map.class);
                    }
                });
    }

    public Mono<ServerResponse> logoutProcessLogic(ServerRequest request) {
        return tokenRepository.findByEmail(jwtResolver.getUserByToken(request)).map(Token::getId)
                .flatMap(tokenRepository::deleteById).then(ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON).body(Mono.just(new SuccessVO(new StatusVO())), SuccessVO.class));
    }

    public Mono<ServerResponse> getCurrentUserProcessLogic(ServerRequest request) {
        return userRepository.findByEmail(jwtResolver.getUserByToken(request))
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON).body(Mono.just(new  UserVO(user)), UserVO.class))
                .switchIfEmpty(ServerResponse.badRequest().body(
                        Mono.just(new ErrorStatusVO(Collections.singletonList("User does not exist")).getErrors()), Map.class));
    }

    public Mono<ServerResponse> updateUserProcessLogic(ServerRequest request) {
        return request.bodyToMono(CurrentUserVO.class)
                .map(CurrentUserVO::getUser)
                .flatMap(userDTO -> {
                    if (validator.validate(userDTO).isEmpty()) {
                        return userRepository.findByEmail(jwtResolver.getUserByToken(request))
                                .flatMap(user -> userRepository.save(user.updateUser(userDTO))
                                .flatMap(savedUser -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON).body(Mono.just(new UserVO(savedUser)), UserVO.class)))
                                .switchIfEmpty(ServerResponse.badRequest().body(
                                        Mono.just(new ErrorStatusVO(Collections.singletonList("User does not exist")).getErrors()), Map.class));
                    }
                    return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON)
                            .body(Mono.just(new ErrorStatusVO(validation(userDTO)).getErrors()), Map.class);
                });
    }

    private void generateToken(User user) {

        List<String> roles = Stream.of(new SimpleGrantedAuthority("USER")).map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        byte[] signingKey = secret.getBytes();

        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject("Reactive-Blog JWT Token")
                .claim("idx", user.getId())
                .claim("email", user.getEmail())
                .claim("userName", user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(expirationTime)))
                .claim("role", roles)
                .compact();

        user.setToken(token);

        Mono.just(user).flatMap(userRepository::save).subscribe();
        Mono.just(Token.builder().id(UUID.randomUUID().toString()).email(user.getEmail()).token(token).build())
                .flatMap(tokenRepository::save).subscribe();
    }

    private <T> List<String> validation(T object) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
        return constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
    }

}
