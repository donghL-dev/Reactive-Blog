package com.donghun.reactiveblog.config.auth;

/**
 * @author donghL-dev
 * @since  2019-12-04
 */
public class SecurityConstants {

    public static final String TOKEN_HEADER = "Authorization";

    public static final String TOKEN_TYPE = "JwtToken";

    public static final String TOKEN_ISSUER = "Reactive-Blog Server";

    public static final String TOKEN_AUDIENCE = "Reactive-Blog Client";

    private SecurityConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
