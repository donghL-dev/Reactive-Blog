package com.donghun.reactiveblog.domain.vo;

import lombok.Getter;

/**
 * @author donghL-dev
 * @since  2019-12-06
 */
@Getter
public class SuccessVO {

    private StatusVO body;

    public SuccessVO(StatusVO statusVO) {
        this.body = statusVO;
    }
}
