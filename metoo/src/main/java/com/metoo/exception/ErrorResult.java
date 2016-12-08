package com.metoo.exception;

/**
 * User: Zhang xiaomei
 * Date: 2016/11/24
 */
public class ErrorResult<T> {

    private Integer code;
    private String message;
    private String url;
    private T data;

    public ErrorResult(Integer code, String message, String url, T data) {
        this.code = code;
        this.message = message;
        this.url = url;
        this.data = data;
    }

    public ErrorResult(ErrorMap error, String url, T data) {
        this.code = error.getCode();
        this.message = error.getMessage();
        this.url = url;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    public T getData() {
        return data;
    }
}
