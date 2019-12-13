package com.donghun.reactiveblog.handler;

import com.donghun.reactiveblog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author donghL-dev
 * @since  2019-12-09
 */
@Component
@RequiredArgsConstructor
public class ArticleHandler {

    private final Logger logger = LoggerFactory.getLogger(ArticleHandler.class);

    private final ArticleService articleService;

    public Mono<ServerResponse> getArticles(ServerRequest request) {
        logger.info("Get Articles Handler Accessed");
        return articleService.getArticlesProcessLogic(request);
    }

    public Mono<ServerResponse> getFeedArticles(ServerRequest request) {
        logger.info("Get Feed Articles Handler Accessed");
        return articleService.getFeedArticlesProcessLogic(request);
    }

    public Mono<ServerResponse> getArticle(ServerRequest request) {
        logger.info("Get Article Handler Accessed");
        return articleService.getArticleProcessLogic(request);
    }

    public Mono<ServerResponse> postArticle(ServerRequest request) {
        logger.info("Post Article Handler Accessed");
        return articleService.postArticleProcessLogic(request);
    }

    public Mono<ServerResponse> putArticle(ServerRequest request) {
        logger.info(("Put Article Handler Accessed"));
        return articleService.putArticleProcessLogic(request);
    }

    public Mono<ServerResponse> deleteArticle(ServerRequest request) {
        logger.info(("Delete Article Handler Accessed"));
        return articleService.deleteArticleProcessLogic(request);
    }
}
