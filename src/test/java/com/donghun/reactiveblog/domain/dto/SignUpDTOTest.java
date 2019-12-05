package com.donghun.reactiveblog.domain.dto;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-02
 */
class SignUpDTOTest {

    @Test
    @DisplayName("SignUpDTO 객체를 생성하는 테스트")
    public void createSignUpDTOTest() {

        // given
        String username = "Test_User";
        String email = "Test_User@email.com";
        String password = "testPassword";

        // when
        SignUpDTO signUpDTO = SignUpDTO.builder()
                            .username(username)
                            .email(email)
                            .password(password)
                            .build();

        // then
        then(signUpDTO).isNotNull();
        then(signUpDTO.getUsername()).isEqualTo(username);
        then(signUpDTO.getEmail()).isEqualTo(email);
        then(signUpDTO.getPassword()).isEqualTo(password);
    }
}