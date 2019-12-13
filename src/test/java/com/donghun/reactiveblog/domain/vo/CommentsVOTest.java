package com.donghun.reactiveblog.domain.vo;

import com.donghun.reactiveblog.domain.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-14
 */
class CommentsVOTest {


    @Test
    @DisplayName("CommentsVO 객체 생성 테스트")
    public void commentVOCreateTest() {

        // given
        List<Comment> comments = Arrays.asList(Comment.builder().body("테스트 댓글").build());

        // when
        CommentsVO commentsVO = new CommentsVO(comments);

        // then
        then(commentsVO).isNotNull();
        then(commentsVO.getComments()).isEqualTo(comments);
    }
}