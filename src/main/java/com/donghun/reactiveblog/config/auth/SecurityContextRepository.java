package com.donghun.reactiveblog.config.auth;

import com.donghun.reactiveblog.repository.TokenRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author donghL-dev
 * @since  2019-12-05
 */
@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private final AuthenticationManager authenticationManager;

    private final TokenRepository tokenRepository;

    @Value("${springbootwebfluxjjwt.jjwt.secret}")
    private String secret;

    public SecurityContextRepository(AuthenticationManager authenticationManager,
                                     TokenRepository tokenRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String authToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authToken != null) {
           return tokenRepository.findByEmail(parseToken(authToken)).flatMap(i -> authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(i.getToken(), i.getToken())).map(SecurityContextImpl::new)
           .switchIfEmpty(Mono.empty()));
        } else {
            return Mono.empty();
        }

    }

    public String parseToken(String token) {
        return (String) Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token).getBody().get("email");
    }
}
