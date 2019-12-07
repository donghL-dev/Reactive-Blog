package com.donghun.reactiveblog.domain.vo;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class SuccessVOTest {

    @Test
    @DisplayName("SuccessVO 객체 생성 테스트")
    public void successVOCreateTest() {

        // given
        StatusVO statusVO = new StatusVO();

        // when
        SuccessVO successVO = new SuccessVO(statusVO);

        // then
        then(successVO).isNotNull();
        then(successVO.getBody()).isEqualTo(statusVO);
    }
}