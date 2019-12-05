package com.donghun.reactiveblog.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
public class SignUpDTO {

    @NotBlank(message = "유저네임이 비어있습니다.")
    @Length(max = 30, min = 2, message = "유저네임은 2~30 사이로 설정하시길 바랍니다.")
    private String username;

    @NotBlank(message = "이메일이 비어있습니다.")
    @Email(message = "유효한 이메일이 아닙니다. 이메일 형식인지 확인해주시길 바랍니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Length(min = 8, max = 22, message = "비밀번호는 8~22 사이로 작성해주서야 합니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,22}$", message = "비밀번호 구성을 올바르게 하십시오.")
    private String password;
}
