package com.donghun.reactiveblog.service;

import com.donghun.reactiveblog.config.auth.JwtResolver;
import com.donghun.reactiveblog.domain.Follow;
import com.donghun.reactiveblog.domain.vo.*;
import com.donghun.reactiveblog.repository.FollowRepository;
import com.donghun.reactiveblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    private final FollowRepository followRepository;

    private final JwtResolver jwtResolver;

    public Mono<ServerResponse> getProfileProcessLogic(ServerRequest request) {
        return userRepository.findByUsername(request.pathVariable("username"))
                .flatMap(user -> followRepository.findByFollowAndFollowing(user.getEmail(), jwtResolver.getUserByToken(request))
                        .flatMap(follow -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                                .body(Mono.just(new ProfileVO(new ProfileBodyVO(user, true))), ProfileVO.class))
                        .switchIfEmpty(ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                                .body(Mono.just(new ProfileVO(new ProfileBodyVO(user, false))), ProfileVO.class))
                        .switchIfEmpty(ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON)
                                .body(Mono.just(new ErrorStatusVO(Collections.singletonList("User does not exist")).getErrors()), Map.class)));
    }

    public Mono<ServerResponse> followUserProcessLogic(ServerRequest request) {
        return userRepository.findByUsername(request.pathVariable("username"))
                .flatMap(user -> {
                    if(user.getEmail().equals(jwtResolver.getUserByToken(request)))
                        return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON)
                                .body(Mono.just(new ErrorStatusVO(Collections.singletonList(
                                        "You can not following yourself.")).getErrors()), Map.class);
                    else {
                        return followRepository.findByFollowAndFollowing(user.getEmail(), jwtResolver.getUserByToken(request))
                                .flatMap(i -> ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON)
                                        .body(Mono.just(new ErrorStatusVO(Collections
                                                .singletonList("You are already Following")).getErrors()), Map.class))
                                .switchIfEmpty(followRepository.save(Follow.builder()
                                        .id(UUID.randomUUID().toString())
                                        .follow(user.getEmail())
                                        .following(jwtResolver.getUserByToken(request)).build())
                                        .flatMap(savedFallow -> ServerResponse.created(URI.create(request.path())).contentType(MediaType.APPLICATION_JSON)
                                                .body(Mono.just(new SuccessVO(new StatusVO())), SuccessVO.class)));
                    }
                }).switchIfEmpty(ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(new ErrorStatusVO(Collections.singletonList("User does not exist")).getErrors()), Map.class));
    }



    public Mono<ServerResponse> unFollowUserProcessLogic(ServerRequest request) {
        return userRepository.findByUsername(request.pathVariable("username"))
                .flatMap(user -> {
                    if(user.getEmail().equals(jwtResolver.getUserByToken(request)))
                        return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON)
                                .body(Mono.just(new ErrorStatusVO(Collections.singletonList(
                                        "You can not un following yourself.")).getErrors()), Map.class);
                    else {
                        return followRepository.findByFollowAndFollowing(user.getEmail(), jwtResolver.getUserByToken(request))
                                .flatMap(id -> followRepository.deleteById(id.getId()).then(ServerResponse.ok().contentType(
                                        MediaType.APPLICATION_JSON).body(Mono.just(new SuccessVO(new StatusVO())), SuccessVO.class)))
                                .switchIfEmpty(ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON)
                                        .body(Mono.just(new ErrorStatusVO(
                                                Collections.singletonList("You are not following this user.")).getErrors()), Map.class));
                    }
                }).switchIfEmpty(ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(new ErrorStatusVO(Collections.singletonList("User does not exist")).getErrors()), Map.class));
    }
}
