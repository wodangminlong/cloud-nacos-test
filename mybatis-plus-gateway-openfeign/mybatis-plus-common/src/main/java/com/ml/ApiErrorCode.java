package com.ml;

/**
 * api response message info
 *
 * @author Administrator
 * @date 2021/10/24 00:00
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
    public static final ApiErrorCode SYSTEM_ERROR = new ApiErrorCode(500, "SYSTEM ERROR");
    public static final ApiErrorCode VOID_REQUEST = new ApiErrorCode(400, "VOID REQUEST");
    public static final ApiErrorCode PARAMETER_ERROR = new ApiErrorCode(400, "PARAMETER ERROR");
    public static final ApiErrorCode REQUESTS_ARE_TOO_FREQUENT = new ApiErrorCode(403, "REQUESTS ARE TOO FREQUENT");
    public static final ApiErrorCode FORBIDDEN = new ApiErrorCode(403, "FORBIDDEN REQUEST");


}
