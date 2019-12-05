package com.donghun.reactiveblog.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class SignUpVOTest {

    @Test
    @DisplayName("SignUpVO 객체 생성 테스트")
    public void createSignUpVOTest() {

        // given, when
        SignUpVO signUpVO = new SignUpVO();

        // then
        then(signUpVO).isNotNull();
    }
}