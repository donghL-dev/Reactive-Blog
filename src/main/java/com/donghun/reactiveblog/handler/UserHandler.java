package com.donghun.reactiveblog.handler;

import com.donghun.reactiveblog.repository.UserRepository;
import com.donghun.reactiveblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
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

    private final UserService userService;

    public Mono<ServerResponse> signUp(ServerRequest request) {
        return userService.signUpProcessLogic(request);
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        return userService.loginProcessLogic(request);
    }

    public Mono<ServerResponse> logout(ServerRequest request) { return  userService.logoutProcessLogic(request); }
}
