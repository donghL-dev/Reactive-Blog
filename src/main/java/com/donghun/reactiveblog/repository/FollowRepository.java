package com.donghun.reactiveblog.repository;

import com.donghun.reactiveblog.domain.Follow;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author donghL-dev
 * @since  2019-12-03
 */
public interface FollowRepository extends ReactiveMongoRepository<Follow, String> {
    Mono<List<Follow>> findByFollow(String follow);

    Mono<Follow> findByFollowAndFollowing(String fallow, String fallowing);

}
