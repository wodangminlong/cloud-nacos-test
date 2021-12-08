package com.ml;

/**
 * @author dml
 * @date 2021/10/25 13:05
 */
public class Constant {

    private Constant() {

    }

    public static final String HTTP_HEADER_APPID = "appid";
    public static final String HTTP_HEADER_SIGN = "authorization";

    public static final String TEST_APPID = "7a1df69775f54d44";
    public static final String TEST_APP_SECRET = "1c67451358824bf2a3c28a8308a6f23a";

    /**
     * default order lock key prefix
     */
    public static final String DEFAULT_ORDER_LOCK_KEY_PREFIX = "DEFAULT_ORDER_LOCK_KEY_PREFIX_";
    /**
     * default order lock time
     */
    public static final long DEFAULT_ORDER_LOCK_TIME = 500 * 1000L;

}
