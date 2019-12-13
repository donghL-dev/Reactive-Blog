package com.donghun.reactiveblog.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-13
 */
class CommentPostVOTest {

    @Test
    @DisplayName("CommentPostVO 객체를 생성하는 테스트")
    public void commentPostVOCreateTest() {

        // given, when
        CommentPostVO commentPostVO = new CommentPostVO();

        // then
        then(commentPostVO).isNotNull();

    }
}