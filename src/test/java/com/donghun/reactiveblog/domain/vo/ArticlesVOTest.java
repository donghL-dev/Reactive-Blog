package com.donghun.reactiveblog.domain.vo;

import com.donghun.reactiveblog.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-12
 */
class ArticlesVOTest {

    @Test
    @DisplayName("ArticlesVO 객체 생성 테스트")
    public void articlesVOCreateTest() {

        // given
        Article article = Article.builder()
                .id(UUID.randomUUID().toString())
                .slug("Hello-World")
                .title("Hello World")
                .description("This is Hello World Article")
                .body("Content")
                .favorites(Collections.emptySet())
                .build();

        List<ArticleVO> articleVOList = Arrays.asList(new ArticleVO(article, "user@email.com"));

        // when
        ArticlesVO articlesVO = new ArticlesVO(articleVOList, 1);

        // then
        then(articlesVO).isNotNull();
    }

}