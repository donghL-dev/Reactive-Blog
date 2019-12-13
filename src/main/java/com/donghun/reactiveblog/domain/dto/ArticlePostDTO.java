package com.donghun.reactiveblog.domain.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @author donghL-dev
 * @since  2019-12-12
 */
@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePostDTO {

    @NotBlank(message = "제목이 비어있습니다.")
    @Length(max = 100, min = 5, message = "제목은 5~100 사이로 설정하시길 바랍니다.")
    private String title;

    @NotBlank(message = "Description 이 비어있습니다.")
    @Length(max = 100, min = 5, message = "Description은 5~100 사이로 설정하시길 바랍니다.")
    private String description;

    @NotBlank(message = "본문이 비어있습니다.")
    @Length(max = 10000, min = 1, message = "본문은 5~10000 사이로 설정하시길 바랍니다.")
    private String body;

    private Set<String> tagList;
}
