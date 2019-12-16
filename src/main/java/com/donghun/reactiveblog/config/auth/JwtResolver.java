package com.donghun.reactiveblog.config.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Base64;
import java.util.List;


/**
 * @author donghL-dev
 * @since  2019-12-05
 */
@Component
public class JwtResolver {

    @Value("${springbootwebfluxjjwt.jjwt.secret}")
    private String secret;

    public String getUserByToken(ServerRequest request) {
        List<String> token = request.headers().header("Authorization");

        Jws<Claims> parsedToken = Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token.get(0));

        return (String) parsedToken.getBody().get("email");
    }

    public String getUserNameByToken(ServerRequest request) {
        List<String> token = request.headers().header("Authorization");

        Jws<Claims> parsedToken = Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token.get(0));

        return (String) parsedToken.getBody().get("userName");
    }
}
