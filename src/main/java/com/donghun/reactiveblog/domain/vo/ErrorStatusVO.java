package com.donghun.reactiveblog.domain.vo;

import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author donghL-dev
 * @since  2019-12-07
 */
@ToString
@Getter
public class ErrorStatusVO {
    private Map<String, Object> errors;

    public ErrorStatusVO(List<String> messages) {
        errors = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("body", messages);
        errors.put("errors", map);
    }
}
