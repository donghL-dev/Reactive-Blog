package com.donghun.reactiveblog.domain.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author donghL-dev
 * @since  2019-12-12
 */
@Getter
@Setter
public class SingleArticleVO {

    private ArticleVO article;

    public SingleArticleVO(ArticleVO article) {
        this.article = article;
    }
}
