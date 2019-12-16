package com.donghun.reactiveblog.route;

import com.donghun.reactiveblog.handler.*;
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

    @Bean
    public RouterFunction<ServerResponse> articleRoutes(ArticleHandler articleHandler) {
        return RouterFunctions.nest(path("/api/articles"),
                route(GET("/"), articleHandler::getArticles)
                .andRoute(GET("/feed"), articleHandler::getFeedArticles)
                .andRoute(GET("/{slug}"), articleHandler::getArticle)
                .andRoute(POST("/").and(contentType(MediaType.APPLICATION_JSON)), articleHandler::postArticle)
                .andRoute(PUT("/{slug}").and(contentType(MediaType.APPLICATION_JSON)), articleHandler::putArticle)
                .andRoute(DELETE("/{slug}"), articleHandler::deleteArticle)
                .andRoute(POST("/{slug}/favorite"), articleHandler::favoriteArticle)
                .andRoute(DELETE("/{slug}/favorite"), articleHandler::unFavoriteArticle));
    }

    @Bean
    public RouterFunction<ServerResponse> commentRoutes(CommentHandler commentHandler) {
        return RouterFunctions.nest(path("/api/articles/{slug}/comments"),
                route(POST("/").and(contentType(MediaType.APPLICATION_JSON)), commentHandler::postComment)
                .andRoute(GET("/"), commentHandler::getComments)
                .andRoute(DELETE("/{id}"), commentHandler::deleteComment));
    }

    @Bean
    public RouterFunction<ServerResponse> tagRoutes(TagHandler tagHandler) {
        return RouterFunctions.route(GET("/api/tags"), tagHandler::getTags);
    }
}
