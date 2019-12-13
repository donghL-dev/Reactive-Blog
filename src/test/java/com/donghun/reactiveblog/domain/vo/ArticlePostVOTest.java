package com.donghun.reactiveblog.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-12
 */
class ArticlePostVOTest {

    @Test
    @DisplayName("ArticlePostVO 객체 생성 테스트")
    public void articlePostVOCreateTest() {

        // given, when
        ArticlePostVO articlePostVO = new ArticlePostVO();

        // then
        then(articlePostVO).isNotNull();
    }

}