package com.donghun.reactiveblog.domain.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-13
 */
class CommentDTOTest {

    @Test
    @DisplayName("CommentDTO 객체를 생성하는 테스트")
    public void commentDTOCreateTest() {

        // given
        String body = "테스트 내용입니다.";

        // when
        CommentDTO commentDTO = CommentDTO.builder().body(body).build();

        // then
        then(commentDTO).isNotNull();
        then(commentDTO.getBody()).isEqualTo(body);
    }

}