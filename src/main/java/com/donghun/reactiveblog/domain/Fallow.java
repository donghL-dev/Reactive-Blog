package com.donghun.reactiveblog.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author donghL-dev
 * @since  2019-12-03
 */
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Document @Builder
public class Fallow {

    @Id
    private String id;

    private String fallow;

    private String fallower;
}
