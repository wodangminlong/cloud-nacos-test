package com.ml;

/**
 * api response message info
 *
 * @author Administrator
 * @date 2021/10/23
 */
public class ApiErrorCode {

    /**
     * error code
     */
    private final int code;

    /**
     * error msg
     */
    private final String msg;

    public ApiErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * system level error
     */
    public static final ApiErrorCode SUCCESS = new ApiErrorCode(0, "SUCCESS");
    public static final ApiErrorCode SYSTEM_ERROR = new ApiErrorCode(5000, "SYSTEM ERROR");
    public static final ApiErrorCode VOID_REQUEST = new ApiErrorCode(4000, "VOID REQUEST");
    public static final ApiErrorCode PARAMETER_ERROR = new ApiErrorCode(4001, "PARAMETER ERROR");
    public static final ApiErrorCode REQUESTS_ARE_TOO_FREQUENT = new ApiErrorCode(4002, "REQUESTS ARE TOO FREQUENT");
    public static final ApiErrorCode FORBIDDEN = new ApiErrorCode(4003, "FORBIDDEN REQUEST");
    public static final ApiErrorCode THE_GOODS_HAVE_BEEN_SOLD_OUT = new ApiErrorCode(4004, "THE GOODS HAVE BEEN SOLD OUT");
    public static final ApiErrorCode OPERATION_IS_TOO_FREQUENT = new ApiErrorCode(4005, "OPERATION IS TOO FREQUENT");


}
