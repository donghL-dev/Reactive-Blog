package com.donghun.reactiveblog.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-03
 */
public class LikeTest {

    @Test
    @DisplayName("Like 객체 생성 테스트")
    public void likeCreateTest() {

        // given
        String article = "Hello-World-Article";
        String author = "Test_User";

        // when
        Like like = Like.builder()
                .id(UUID.randomUUID().toString())
                .article(article)
                .likeUser(author)
                .build();

        // then
        then(like).isNotNull();
        then(like.getArticle()).isEqualTo(article);
        then(like.getLikeUser()).isEqualTo(author);
    }
}