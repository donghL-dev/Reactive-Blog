package com.donghun.reactiveblog.domain.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author donghL-dev
 * @since  2019-12-13
 */
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    @NotBlank(message = "내용이 비어있습니다.")
    @Length(max = 500, min = 1, message = "내용은 1~500 사이로 설정하시길 바랍니다.")
    private String body;
}
