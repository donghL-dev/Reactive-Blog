package com.donghun.reactiveblog.domain.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-02
 */
class LoginDTOTest {

    @Test
    @DisplayName("LoginDTO 객체 생성 테스트")
    public void loginDTOCreateTest() {

        // given
        String email = "test@email.com";
        String password = "testPassword";

        // when
        LoginDTO loginDTO = LoginDTO.builder()
                            .email(email)
                            .password(password)
                            .build();

        // then
        then(loginDTO).isNotNull();
        then(loginDTO.getEmail()).isEqualTo(email);
        then(loginDTO.getPassword()).isEqualTo(password);
    }
}