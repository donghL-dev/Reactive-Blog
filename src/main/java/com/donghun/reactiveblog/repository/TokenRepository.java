package com.donghun.reactiveblog.repository;

import com.donghun.reactiveblog.domain.Token;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * @author donghL-dev
 * @since  2019-12-04
 */
public interface TokenRepository extends ReactiveMongoRepository<Token, String> {
    Mono<Token> findByEmail(String email);
}
