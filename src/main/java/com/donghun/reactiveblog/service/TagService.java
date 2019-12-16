package com.donghun.reactiveblog.service;

import com.donghun.reactiveblog.domain.Tag;
import com.donghun.reactiveblog.domain.vo.TagsVO;
import com.donghun.reactiveblog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

/**
 * @author donghL-dev
 * @since  2019-12-14
 */
@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Mono<ServerResponse> getTagsProcessLogic(ServerRequest request) {
        return tagRepository.findAll().map(Tag::getName)
                .collectList().flatMap(tags -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(new TagsVO(tags.stream().sorted().collect(Collectors.toList()))), TagsVO.class));
    }
}
