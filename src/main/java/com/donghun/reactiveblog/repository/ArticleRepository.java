package com.donghun.reactiveblog.repository;

import com.donghun.reactiveblog.domain.Article;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * @author donghL-dev
 * @since  2019-12-02
 */
public interface ArticleRepository extends ReactiveMongoRepository<Article, String> {
    Mono<Article> findBySlug(String slug);
}
