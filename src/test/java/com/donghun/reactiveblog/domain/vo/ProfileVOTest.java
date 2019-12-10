package com.donghun.reactiveblog.domain.vo;

import com.donghun.reactiveblog.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author donghL-dev
 * @since  2019-12-10
 */
class ProfileVOTest {

    @Test
    @DisplayName("ProfileVO 객체 생성 테스트")
    public void profileVOCreateTest() {

        // given
        User user = User.builder()
                .image("test_image")
                .bio("test_bio")
                .username("test_username")
                .build();

        ProfileBodyVO profileBodyVO = new ProfileBodyVO(user, false);

        // when
        ProfileVO profileVO = new ProfileVO(profileBodyVO);

        // then
        then(profileVO).isNotNull();
        then(profileVO.getProfile()).isEqualTo(profileBodyVO);
    }
}