package com.donghun.reactiveblog.domain.vo;

import com.donghun.reactiveblog.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author donghL-dev
 * @since  2019-12-09
 */
@Getter
@Setter
public class ProfileBodyVO {

    private String image;

    private String bio;

    private String username;

    private Boolean fallowing;

    public ProfileBodyVO(User user, boolean check) {
        this.image = user.getImage();
        this.bio = user.getBio();
        this.username = user.getUsername();
        this.fallowing = check;
    }
}
