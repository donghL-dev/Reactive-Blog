package com.donghun.reactiveblog.handler;

import com.donghun.reactiveblog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author donghL-dev
 * @since  2019-12-14
 */
@Component
@RequiredArgsConstructor
public class TagHandler {

    private final Logger logger = LoggerFactory.getLogger(TagHandler.class);

    private final TagService tagService;

    public Mono<ServerResponse> getTags(ServerRequest request) {
        logger.info("Get Tags Handler Accessed");
        return tagService.getTagsProcessLogic(request);
    }
}
