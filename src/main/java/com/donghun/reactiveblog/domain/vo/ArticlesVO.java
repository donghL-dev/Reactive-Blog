package com.donghun.reactiveblog.domain.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author donghL-dev
 * @since  2019-12-10
 */
@Getter
@Setter
public class ArticlesVO {

    private List<ArticleVO> articles;

    private Integer articlesCount;

    public ArticlesVO(List<ArticleVO> articlesVOS, Integer articlesCount) {
        this.articles = articlesVOS;
        this.articlesCount = articlesCount;
    }
}
