package com.donghun.reactiveblog.domain.vo;

import com.donghun.reactiveblog.domain.Comment;
import lombok.Getter;
import lombok.Setter;

/**
 * @author donghL-dev
 * @since  2019-12-13
 */
@Getter @Setter
public class CommentVO {

    private Comment comment;

    public CommentVO(Comment comment) {
        this.comment = comment;
    }
}
