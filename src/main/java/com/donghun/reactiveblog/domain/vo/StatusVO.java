package com.donghun.reactiveblog.domain.vo;

import lombok.Getter;

/**
 * @author donghL-dev
 * @since  2019-12-06
 */
@Getter
public class StatusVO {

    private String status;

    private String message;

    public StatusVO() {
        this.status = "200 OK";
        this.message = "Your request has been successfully processed.";
    }
}
