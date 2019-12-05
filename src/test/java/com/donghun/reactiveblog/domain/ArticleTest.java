package com.donghun.reactiveblog.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-02
 */
public class ArticleTest {

    @Test
    @DisplayName("Article 객체 생성 테스트")
    public void articleCreateTest() {

        // given
        String slug = "Hello-World";
        String title = "Hello World";
        String description = "헬로우 월드 게시글입니다.";
        String body = "헬로우 월드는 모든 개발자의 희망입니다.";
        String author = "Test_User";

        // when
        Article article = Article.builder()
                .id(UUID.randomUUID().toString())
                .slug(slug)
                .title(title)
                .description(description)
                .body(body)
                .author(author)
                .createdAt(LocalDateTime.now())
                .build();

        // then
        then(article).isNotNull();
        then(article.getSlug()).isEqualTo(slug);
        then(article.getTitle()).isEqualTo(title);
        then(article.getDescription()).isEqualTo(description);
        then(article.getBody()).isEqualTo(body);
        then(article.getAuthor()).isEqualTo(author);
    }
}