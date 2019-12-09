package com.donghun.reactiveblog.domain.dto;

import com.donghun.reactiveblog.config.vaild.SomeUtilClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

/**
 * @author donghL-dev
 * @since  2019-12-07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @Pattern(regexp = SomeUtilClass.UUID_PATTERN, message = "id must be uuid")
    private String id;

    @Email(message = "유효한 이메일이 아닙니다. 이메일 형식인지 확인해주시길 바랍니다.")
    private String email;

    @Length(max = 30, message = "유저네임은 30자 미만으로 설정하시길 바랍니다.")
    private String username;

    @URL(message = "올바른 이미지 URL이 아닙니다.")
    private String image;

    @Length(max = 150, message = "bio는 150자 이내로 설정하시길 바랍니다.")
    private String bio;

}
