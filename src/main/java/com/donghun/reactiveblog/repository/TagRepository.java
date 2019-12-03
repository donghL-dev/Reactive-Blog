package com.donghun.reactiveblog.repository;

import com.donghun.reactiveblog.domain.Tag;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * @author donghL-dev
 * @since  2019-12-02
 */
public interface TagRepository extends ReactiveMongoRepository<Tag, String> {
    Mono<Tag> findByName(String name);
}
