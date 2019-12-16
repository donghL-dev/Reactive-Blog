package com.donghun.reactiveblog.domain;

import com.donghun.reactiveblog.config.auth.JwtResolver;
import com.donghun.reactiveblog.domain.dto.ArticlePostDTO;
import com.donghun.reactiveblog.domain.dto.ArticlePutDTO;
import com.donghun.reactiveblog.domain.vo.ProfileBodyVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

/**
 * @author donghL-dev
 * @since  2019-12-02
 */
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Document @Builder
public class Article {

    @Id
    private String id;

    private String slug;

    private String title;

    private String description;

    private String body;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    private ProfileBodyVO author;

    private Set<String> tags;

    private Set<String> favorites;

    private Integer favoritesCount;

    public Article createArticle(ArticlePostDTO articlePostDTO, User user) {
        return Article.builder()
                .id(UUID.randomUUID().toString())
                .slug(articlePostDTO.getTitle().replace(" ", "-"))
                .title(articlePostDTO.getTitle())
                .description(articlePostDTO.getDescription())
                .body(articlePostDTO.getBody())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .favorites(Collections.emptySet())
                .author(new ProfileBodyVO(user, false))
                .favoritesCount(0)
                .tags(articlePostDTO.getTagList()).build();
    }

    public Article updateArticle(ArticlePutDTO articlePutDTO) {
        this.slug = articlePutDTO.getTitle() == null ? this.getSlug()
                : articlePutDTO.getTitle().replace(" ", "-");
        this.title = articlePutDTO.getTitle() == null ? this.getTitle() : articlePutDTO.getTitle();
        this.body = articlePutDTO.getBody() == null ? this.getBody() : articlePutDTO.getBody();
        this.tags = articlePutDTO.getTagList() == null ? this.getTags() : articlePutDTO.getTagList();
        this.description = articlePutDTO.getDescription() == null
                        ? this.getDescription() : articlePutDTO.getDescription();
        this.updatedAt = LocalDateTime.now();

        return this;
    }

    public Article favoriteArticle(JwtResolver jwtResolver, ServerRequest request) {
        this.getFavorites().add(jwtResolver.getUserByToken(request));
        this.getFavorites().add(jwtResolver.getUserNameByToken(request));
        this.favoritesCount = this.getFavoritesCount() + 1;

        return this;
    }


    public Article unFavoriteArticle(JwtResolver jwtResolver, ServerRequest request) {
        this.getFavorites().remove(jwtResolver.getUserByToken(request));
        this.getFavorites().remove(jwtResolver.getUserNameByToken(request));
        this.favoritesCount = this.getFavoritesCount() - 1;

        return this;
    }
}
