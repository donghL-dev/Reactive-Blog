package com.donghun.reactiveblog.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-09
 */
class ErrorStatusVOTest {

    @Test
    @DisplayName("ErrorStatusVO 객체 생성 테스트")
    public void createErrorStatusVOTest() {

        // given
        List<String> messages = Collections.singletonList("Error Message");

        // when
        ErrorStatusVO errorStatusVO = new ErrorStatusVO(messages);

        // then
        then(errorStatusVO).isNotNull();
    }
}