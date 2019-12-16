package com.donghun.reactiveblog.domain;

import com.donghun.reactiveblog.domain.dto.CommentDTO;
import com.donghun.reactiveblog.domain.vo.ProfileBodyVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
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
public class Comment {

    @Id
    private String id;

    private String body;

    private ProfileBodyVO author;

    @JsonIgnore
    private String slug;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    public Comment createComment(CommentDTO commentDTO, User user, Article article) {
        return Comment.builder()
                .id(UUID.randomUUID().toString())
                .body(commentDTO.getBody())
                .author(new ProfileBodyVO(user, false))
                .slug(article.getSlug())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
