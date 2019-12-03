package com.donghun.reactiveblog.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
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

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String author;

    private Set<String> tags = new HashSet<>();

}
