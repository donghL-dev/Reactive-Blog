package com.donghun.reactiveblog.handler;

import com.donghun.reactiveblog.service.ProfileService;
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
public class ProfileHandler {

    private final Logger logger = LoggerFactory.getLogger(ProfileHandler.class);

    private final ProfileService profileService;

    public Mono<ServerResponse> getProfile(ServerRequest request) {
        logger.info("Get Profile Handler Accessed");
        return profileService.getProfileProcessLogic(request);
    }

    public Mono<ServerResponse> fallowUser(ServerRequest request) {
        logger.info("Fallow User Handler Accessed");
        return profileService.followUserProcessLogic(request);
    }

    public Mono<ServerResponse> unFallowUser(ServerRequest request) {
        logger.info("UnFallow User Handler Accessed");
        return profileService.unFollowUserProcessLogic(request);
    }
}
