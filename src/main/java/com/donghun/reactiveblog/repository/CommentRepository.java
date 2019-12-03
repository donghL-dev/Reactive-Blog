package com.donghun.reactiveblog.repository;

import com.donghun.reactiveblog.domain.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * @author donghL-dev
 * @since  2019-12-02
 */
public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    Mono<Comment> findByBodyAndAuthorAndSlug(String body, String slug, String author);
}
