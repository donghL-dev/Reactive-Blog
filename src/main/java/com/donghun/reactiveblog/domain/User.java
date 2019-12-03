package com.donghun.reactiveblog.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author donghL-dev
 * @since  2019-12-01
 */
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Document @Builder
public class User {

    @Id
    private String id;

    private String username;

    private String email;

    private String password;

    private String bio;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String image;

    private String token;
}
