package com.donghun.reactiveblog.domain.vo;

import com.donghun.reactiveblog.domain.Comment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author donghL-dev
 * @since  2019-12-14
 */
@Getter
@Setter
public class CommentsVO {

    private List<Comment> comments;

    public CommentsVO(List<Comment> comments) {
        this.comments = comments;
    }
}
