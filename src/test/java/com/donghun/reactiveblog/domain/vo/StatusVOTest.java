package com.donghun.reactiveblog.domain.vo;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-06
 */
class StatusVOTest {

    @Test
    @DisplayName("StatusVO 객체 생성 테스트")
    public void statusVOCreateTest() {

        // given
        String status = "200 OK";
        String message = "Your request has been successfully processed.";

        // when
        StatusVO statusVO = new StatusVO();

        // then
        then(statusVO).isNotNull();
        then(statusVO.getMessage()).isEqualTo(message);
        then(statusVO.getStatus()).isEqualTo(status);
    }

}