package com.donghun.reactiveblog.domain.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

/**
 * @author donghL-dev
 * @since  2019-12-12
 */
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePutDTO {

    @Length(max = 100, min = 5, message = "제목은 5~100 사이로 설정하시길 바랍니다.")
    private String title;

    @Length(max = 100, min = 5, message = "Description은 5~100 사이로 설정하시길 바랍니다.")
    private String description;

    @Length(max = 10000, min = 1, message = "본문은 5~10000 사이로 설정하시길 바랍니다.")
    private String body;

    private Set<String> tagList;

}
