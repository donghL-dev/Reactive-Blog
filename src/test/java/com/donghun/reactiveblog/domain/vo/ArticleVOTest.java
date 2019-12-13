package com.donghun.reactiveblog.domain.vo;

import com.donghun.reactiveblog.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-12
 */
class ArticleVOTest {

    @Test
    @DisplayName("ArticleVO 객체 생성 테스트")
    public void articleVOCreateTest() {
        // given
        Article article = Article.builder()
                .id(UUID.randomUUID().toString())
                .slug("Hello-World")
                .title("Hello World")
                .description("This is Hello World Article")
                .body("Content")
                .favorites(Collections.emptySet())
                .build();

        // when
        ArticleVO articleVO = new ArticleVO(article, "user@meial.com");

        // then
        then(articleVO).isNotNull();
        then(articleVO.getSlug()).isEqualTo(article.getSlug());
        then(articleVO.getTitle()).isEqualTo(article.getTitle());
        then(articleVO.getDescription()).isEqualTo(article.getDescription());
        then(articleVO.getBody()).isEqualTo(article.getBody());
    }

}