package com.donghun.reactiveblog.domain.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author donghL-dev
 * @since  2019-12-14
 */
@Getter @Setter
public class TagsVO {

    private List<String> tags;

    public TagsVO(List<String> tags) {
        this.tags = tags;
    }
}
