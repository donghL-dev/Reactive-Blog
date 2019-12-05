package com.donghun.reactiveblog.config;

import com.donghun.reactiveblog.config.auth.AuthenticationManager;
import com.donghun.reactiveblog.config.auth.SecurityContextRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * @author donghL-dev
 * @since  2019-12-03
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final AuthenticationManager authenticationManager;

    private final SecurityContextRepository securityContextRepository;

    public SecurityConfig(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.cors()
                .and()
                    .csrf().csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse()).disable()
                    .httpBasic()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() -> {
                        swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    })).accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> {
                        swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    }))
                .and()
                    .authorizeExchange()
                        .pathMatchers(HttpMethod.POST, "/api/users/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/profiles/**", "/api/articles",
                                "/api/articles/{slug}/comments", "/api/tags").permitAll()
                        .anyExchange().authenticated()
                .and()
                    .authenticationManager(authenticationManager)
                    .securityContextRepository(securityContextRepository)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin(CorsConfiguration.ALL);
        configuration.addAllowedMethod(CorsConfiguration.ALL);
        configuration.addAllowedHeader(CorsConfiguration.ALL);

        configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Authorization", "x-xsrf-token",
                "Access-Control-Allow-Headers", "Origin", "Accept", "X-Requested-With",
                "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
