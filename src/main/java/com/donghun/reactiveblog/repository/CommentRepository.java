package com.donghun.reactiveblog.repository;

import com.donghun.reactiveblog.domain.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author donghL-dev
 * @since  2019-12-02
 */
public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    Flux<Comment> findBySlug(String slug);
}
