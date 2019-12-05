package com.donghun.reactiveblog.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author donghL-dev
 * @since  2019-12-04
 */
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Document @Builder
public class Token {

    @Id
    private String id;

    private String email;

    private String token;
}
