package com.donghun.reactiveblog.repository;

import com.donghun.reactiveblog.domain.Article;
import com.donghun.reactiveblog.domain.vo.ProfileBodyVO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author donghL-dev
 * @since  2019-12-02
 */
public interface ArticleRepository extends ReactiveMongoRepository<Article, String> {
    Mono<Article> findBySlug(String slug);

    Flux<Article> findByAuthor(ProfileBodyVO author);
}
