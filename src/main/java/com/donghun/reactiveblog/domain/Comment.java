package com.donghun.reactiveblog.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

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

    private String author;

    private String slug;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
