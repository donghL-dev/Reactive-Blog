package com.donghun.reactiveblog.domain.vo;

import com.donghun.reactiveblog.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-10
 */
class ProfileBodyVOTest {

    @Test
    @DisplayName("ProfileBodyVO 객체 생성 테스트")
    public void profileBodyVOCreateTest() {

        // given
        User user = User.builder()
                        .image("test_image")
                        .bio("test_bio")
                        .username("test_username")
                        .build();
        // when
        ProfileBodyVO profileBodyVO = new ProfileBodyVO(user, false);

        // then
        then(profileBodyVO).isNotNull();
        then(profileBodyVO.getImage()).isEqualTo(user.getImage());
        then(profileBodyVO.getBio()).isEqualTo(user.getBio());
        then(profileBodyVO.getUsername()).isEqualTo(user.getUsername());
        then(profileBodyVO.getFallowing()).isEqualTo(false);
    }

}