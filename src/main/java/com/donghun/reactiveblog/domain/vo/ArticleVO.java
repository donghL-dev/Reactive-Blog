package com.donghun.reactiveblog.domain.vo;

import com.donghun.reactiveblog.domain.Article;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author donghL-dev
 * @since  2019-12-10
 */
@Getter
@Setter
public class ArticleVO {

    private String id;

    private String slug;

    private String title;

    private String body;

    private String description;

    private Integer favoritesCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    private Boolean favorited;

    private ProfileBodyVO author;

    private Set<String> tagList;

    public ArticleVO(Article article, String email) {
        this.id = article.getId();
        this.slug = article.getSlug();
        this.title = article.getTitle();
        this.body = article.getBody();
        this.description = article.getDescription();
        this.favoritesCount = article.getFavoritesCount();
        this.createdAt = article.getCreatedAt();
        this.updatedAt = article.getUpdatedAt();
        this.favorited = article.getFavorites().contains(email);
        this.author = article.getAuthor();
        this.tagList = article.getTags();
    }
}
