package com.donghun.reactiveblog.domain.vo;

import com.donghun.reactiveblog.domain.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-13
 */
class CommentVOTest {

    @Test
    @DisplayName("CommentVO 객체를 생성하는 테스트")
    public void commentVOCreateTest() {

        // given
        Comment comment = Comment.builder()
                .id(UUID.randomUUID().toString())
                .body("테스트 댓글").build();

        // when
        CommentVO commentVO = new CommentVO(comment);

        // then
        then(commentVO).isNotNull();
        then(commentVO.getComment()).isEqualTo(comment);
    }

}