package com.donghun.reactiveblog.domain.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-07
 */
class UserDTOTest {

    @Test
    @DisplayName("UserDTO 객체 생성 테스트")
    public void userVOCreateTest() {

        // given
        String id = UUID.randomUUID().toString();
        String username = "testName";
        String email = "test@Email.com";
        String image = "normal image";
        String bio = "I'm Hello World";

        // when
        UserDTO userDTOTest = UserDTO.builder()
                            .id(id)
                            .username(username)
                            .email(email)
                            .image(image)
                            .bio(bio)
                            .build();

        // then
        then(userDTOTest).isNotNull();
        then(userDTOTest.getId()).isEqualTo(id);
        then(userDTOTest.getUsername()).isEqualTo(username);
        then(userDTOTest.getEmail()).isEqualTo(email);
        then(userDTOTest.getImage()).isEqualTo(image);
        then(userDTOTest.getBio()).isEqualTo(bio);
    }

}