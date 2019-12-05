package com.donghun.reactiveblog.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-05
 */
class TokenTest {

    @Test
    @DisplayName("Token 객체 생성 테스트")
    public void tokenCreateTest() {

        // given
        String username = "Test_user";
        String token = "5v8y/B?D(G+KbPeShVmYq3t6w9z$C&F)H@McQfTjWnZr4u7x!A%D*G-KaNdRgUkX";

        // when
        Token tokenObject = Token.builder()
                .id(UUID.randomUUID().toString())
                .email(username)
                .token(token).build();

        // then
        then(tokenObject).isNotNull();
        then(tokenObject.getEmail()).isEqualTo(username);
        then(tokenObject.getToken()).isEqualTo(token);
    }

}