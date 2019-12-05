package com.donghun.reactiveblog.config.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author donghL-dev
 * @since  2019-12-05
 */
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Value("${springbootwebfluxjjwt.jjwt.secret}")
    private String secret;

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {

        String authToken = authentication.getCredentials().toString();
        String email = "";

        try {
            email = getEmailFromToken(authToken);
        } catch (Exception e) {
            email = null;
        }

        if(email != null && validateToken(authToken)) {
            Claims claims = getAllClaimsFromToken(authToken);
            List<String> roles = claims.get("role", List.class);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null,
                    roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            return Mono.just(auth);
        } else {
            return Mono.empty();
        }
    }

    public String getEmailFromToken(String token) {
        return (String) getAllClaimsFromToken(token).get("email");
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getAllClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }
}
