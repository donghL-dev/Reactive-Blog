package com.donghun.reactiveblog.domain;

import com.donghun.reactiveblog.domain.vo.ProfileBodyVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

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

}
