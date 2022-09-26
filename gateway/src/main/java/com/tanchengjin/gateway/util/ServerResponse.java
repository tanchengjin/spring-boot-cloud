package com.tanchengjin.gateway.util;

import java.io.Serializable;

/**
 * @author TanChengjin
 * @version v1.0.0
 * @email 18865477815@163.com
 */
public final class ServerResponse<T> implements Serializable {
    private int code;
    private T data;
    private String message;

    private ServerResponse() {
    }

    private ServerResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static <T> ServerResponse<T> responseWithSuccess() {
        return new ServerResponse<T>(0, null, "success");
    }

    public static <T> ServerResponse<T> responseWithSuccess(T data) {
        return new ServerResponse<T>(0, data, "success");
    }

    public static <T> ServerResponse<T> responseWithSuccess(T data, String message) {
        return new ServerResponse<T>(0, data, "success");
    }

    public static <T> ServerResponse<T> responseWithSuccessMessage(String message) {
        return new ServerResponse<T>(0, null, message);
    }

    public static <T> ServerResponse<T> responseWithSuccessMessage(String message, T data) {
        return new ServerResponse<T>(0, data, message);
    }

    public static <T> ServerResponse<T> responseWithFailure() {
        return new ServerResponse<T>(1, null, "success");
    }


    public static <T> ServerResponse<T> responseWithFailureMessage(String message) {
        return new ServerResponse<T>(1, null, message);
    }

    public static <T> ServerResponse<T> responseWithFailure(String message, T data) {
        return new ServerResponse<T>(1, data, message);
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
