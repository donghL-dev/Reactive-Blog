package com.donghun.reactiveblog.domain.vo;

import com.donghun.reactiveblog.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;

class UserVOTest {

    @Test
    @DisplayName("UserVO 객체 생성 테스트")
    public void userVoCreateTest() {

        // given
        String id = UUID.randomUUID().toString();
        String username = "test_user";
        String email = "test_user@email.com";
        String password = "test_password";
        String bio = "나는 Test_User 입니다.";
        LocalDateTime createdAt = LocalDateTime.now();

        // when
        User user = User.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .bio(bio)
                .createdAt(createdAt)
                .build();

        // when
        UserVO userVO = new UserVO(user);

        // then
        then(userVO).isNotNull();
        then(userVO.getUser()).isEqualTo(user);
    }
}