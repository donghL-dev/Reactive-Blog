package com.donghun.reactiveblog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author donghL-dev
 * @since  2019-12-04
 */
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    private String email;

    private String password;
}
