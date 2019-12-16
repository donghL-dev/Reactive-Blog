package com.donghun.reactiveblog.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author donghL-dev
 * @since  2019-12-09
 */
public class GlobalException extends ResponseStatusException {

    public GlobalException(HttpStatus status, String message) {
        super(status, message);
    }

    public GlobalException(HttpStatus status, String message, Throwable e) {
        super(status, message, e);
    }
}
