package com.donghun.reactiveblog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author donghL-dev
 * @since  2019-12-04
 */
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotBlank(message = "이메일이 비어있습니다.")
    @Email(message = "유효한 이메일이 아닙니다. 이메일 형식인지 확인해주시길 바랍니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Length(min = 8, max = 22, message = "비밀번호는 8~22 사이로 작성해주서야 합니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,22}$", message = "비밀번호 구성을 올바르게 하십시오.")
    private String password;
}
