package com.donghun.reactiveblog.domain.vo;


import lombok.Getter;
import lombok.Setter;

/**
 * @author donghL-dev
 * @since  2019-12-09
 */
@Getter
@Setter
public class ProfileVO {

    private ProfileBodyVO profile;

    public ProfileVO(ProfileBodyVO profile) {
        this.profile = profile;
    }
}
