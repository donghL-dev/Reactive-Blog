package com.donghun.reactiveblog.route;

import com.donghun.reactiveblog.handler.ProfileHandler;
import com.donghun.reactiveblog.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author donghL-dev
 * @since  2019-12-04
 */
@Configuration
@EnableWebFlux
public class ServerRouter {

    @Bean
    public RouterFunction<ServerResponse> userRoutes(UserHandler userHandler) {
        return RouterFunctions.nest(path("/api/users"),
                route(POST("/").and(contentType(MediaType.APPLICATION_JSON)), userHandler::signUp)
                .andRoute(POST("/login").and(contentType(MediaType.APPLICATION_JSON)), userHandler::login)
                .andRoute(POST("/logout"), userHandler::logout))
                .andNest(path("/api/user"),
                        route(GET("/"), userHandler::currentUser)
                        .andRoute(PUT("/").and(contentType(MediaType.APPLICATION_JSON)), userHandler::updateUser));
    }

    @Bean
    public RouterFunction<ServerResponse> profileRoutes(ProfileHandler profileHandler) {
        return RouterFunctions.nest(path("/api/profile/{username}"),
                route(GET("/"), profileHandler::getProfile)
                .andRoute(POST("/follow"), profileHandler::fallowUser)
                .andRoute(DELETE("/follow"), profileHandler::unFallowUser));
    }
}
