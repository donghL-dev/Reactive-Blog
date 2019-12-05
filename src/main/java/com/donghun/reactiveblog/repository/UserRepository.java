package com.donghun.reactiveblog.repository;

import com.donghun.reactiveblog.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * @author donghL-dev
 * @since  2019-12-01
 */
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByEmail(String email);
}
