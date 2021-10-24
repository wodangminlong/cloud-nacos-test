package com.ml;

import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * uniform return type
 *
 * @author Administrator
 * @date 2021/10/24 00:01
 */
@NoArgsConstructor
public class ApiResponse implements Serializable {

    private int code;
    private String msg;
    private transient Object data;

    public ApiResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ApiResponse(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public static ApiResponse success() {
        return new ApiResponse(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg());
    }

    public static ApiResponse success(Object data) {
        return new ApiResponse(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), data);
    }

    public static ApiResponse error() {
        return new ApiResponse(ApiErrorCode.SYSTEM_ERROR.getCode(), ApiErrorCode.SYSTEM_ERROR.getMsg());
    }

    public static ApiResponse error(ApiErrorCode apiErrorCode) {
        return new ApiResponse(apiErrorCode.getCode(), apiErrorCode.getMsg());
    }

    @Override
    public String toString() {
        return "{code:" + this.getCode() + ", msg:" + this.getMsg() + ", data:" + this.getData() + "}";
    }

}
