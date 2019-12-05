package com.donghun.reactiveblog.domain.vo;

import com.donghun.reactiveblog.domain.User;
import lombok.Getter;
import lombok.ToString;

/**
 * @author donghL-dev
 * @since  2019-12-05
 */
@Getter
@ToString
public class UserVO {

    private User user;

    public UserVO(User user) {
        this.user = user;
    }
}
