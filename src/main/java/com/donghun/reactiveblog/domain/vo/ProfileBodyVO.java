package com.donghun.reactiveblog.domain.vo;

import com.donghun.reactiveblog.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

/**
 * @author donghL-dev
 * @since  2019-12-09
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProfileBodyVO {

    private String image;

    private String bio;

    private String username;

    private Boolean fallowing;

    @JsonIgnore
    private String email;

    public ProfileBodyVO(User user, boolean check) {
        this.image = user.getImage();
        this.bio = user.getBio();
        this.username = user.getUsername();
        this.fallowing = check;
        this.email = user.getEmail();
    }
}
