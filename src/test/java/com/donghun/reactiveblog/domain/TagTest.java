package com.donghun.reactiveblog.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-02
 */
public class TagTest {

    @Test
    @DisplayName("Tag 객체 생성 테스트")
    public void tagCreateTest() {

        // given
        String name = "react.js";

        // when
        Tag tag = Tag.builder()
                .id(UUID.randomUUID().toString())
                .name(name).build();

        // then
        then(tag).isNotNull();
        then(tag.getName()).isEqualTo(name);
    }
}