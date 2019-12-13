package com.donghun.reactiveblog.handler;

import com.donghun.reactiveblog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author donghL-dev
 * @since  2019-12-13
 */
@Component
@RequiredArgsConstructor
public class CommentHandler {

    private final Logger logger = LoggerFactory.getLogger(CommentHandler.class);

    private final CommentService commentService;

    public Mono<ServerResponse> postComment(ServerRequest request) {
        logger.info("Post Comment Handler Accessed");
        return commentService.postCommentProcessLogic(request);
    }

    public Mono<ServerResponse> getComments(ServerRequest request) {
        logger.info("Get Comments Handler Accessed");
        return commentService.getCommentsProcessLogic(request);
    }

    public Mono<ServerResponse> deleteComment(ServerRequest request) {
        logger.info("Delete Comment Handler Accessed");
        return commentService.deleteCommentProcessLogic(request);
    }
}
