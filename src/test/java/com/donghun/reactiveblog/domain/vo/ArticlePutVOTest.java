package com.donghun.reactiveblog.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-12
 */
class ArticlePutVOTest {

    @Test
    @DisplayName("ArticlePutVO 객체 생성 테스트")
    public void articlePutVOCreateTest() {

        // given, when
        ArticlePutVO articlePutVO = new ArticlePutVO();

        // then
        then(articlePutVO).isNotNull();
    }
}