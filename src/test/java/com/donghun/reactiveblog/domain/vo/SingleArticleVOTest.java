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
class SingleArticleVOTest {

    @Test
    @DisplayName("SingelArticleVO 객체 생성 테스트")
    public void singleArticleVOCreateTest() {

        // given
        Article article = Article.builder()
                .id(UUID.randomUUID().toString())
                .slug("Hello-World")
                .title("Hello World")
                .description("This is Hello World Article")
                .body("Content")
                .favorites(Collections.emptySet())
                .build();

        ArticleVO articleVO = new ArticleVO(article, "user@meial.com");

        // when
        SingleArticleVO singleArticleVO = new SingleArticleVO(articleVO);

        // then
        then(singleArticleVO).isNotNull();
    }
}