package com.donghun.reactiveblog.domain.vo;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-06
 */
class CurrentUserVOTest {

    @Test
    @DisplayName("CurrentUserVO 객체 생성 테스트")
    public void currentUserVOCreateTest() {

        // given, when
        CurrentUserVO userVO = new CurrentUserVO();

        // then
        then(userVO).isNotNull();
    }
}