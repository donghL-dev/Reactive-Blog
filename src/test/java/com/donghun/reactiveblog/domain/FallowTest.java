package com.donghun.reactiveblog.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-02
 */
public class FallowTest {

    @Test
    @DisplayName("Fallow 객체 생성 테스트")
    public void fallowCreateTest() {

        // given
        String fallow = "Test_USER_A";
        String fallower = "TEST_USER_B";

        // when
        Fallow fallow1 = Fallow.builder()
                    .id(UUID.randomUUID().toString())
                    .fallow(fallow)
                    .fallower(fallower)
                    .build();

        // then
        then(fallow1).isNotNull();
        then(fallow1.getFallow()).isEqualTo(fallow);
        then(fallow1.getFallower()).isEqualTo(fallower);
    }
}