package com.donghun.reactiveblog.handler;

import com.donghun.reactiveblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author donghL-dev
 * @since  2019-12-04
 */
@Component
@RequiredArgsConstructor
public class UserHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserHandler.class);

    private final UserService userService;

    public Mono<ServerResponse> signUp(ServerRequest request) {
        logger.info("sing up Handler Accessed");
        return userService.signUpProcessLogic(request);
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        logger.info("Login Handler Accessed");
        return userService.loginProcessLogic(request);
    }

    public Mono<ServerResponse> logout(ServerRequest request) {
        logger.info("Logout Handler Accessed");
        return  userService.logoutProcessLogic(request);
    }

    public Mono<ServerResponse> currentUser(ServerRequest request) {
        logger.info("Current User Handler Accessed");
        return userService.getCurrentUserProcessLogic(request);
    }

    public Mono<ServerResponse> updateUser(ServerRequest request) {
        logger.info("Update User Handler Accessed");
        return userService.updateUserProcessLogic(request);
    }
}
