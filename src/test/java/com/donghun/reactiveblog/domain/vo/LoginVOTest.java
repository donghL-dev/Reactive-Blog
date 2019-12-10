package com.donghun.reactiveblog.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-05
 */
class LoginVOTest {

    @Test
    @DisplayName("LoginVO 객체를 생성하는 테스트")
    public void loginVOCreateTest() {

        // given, when
        LoginVO loginVO = new LoginVO();

        // then
        then(loginVO).isNotNull();
    }

}