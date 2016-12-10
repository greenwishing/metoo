package com.metoo.exception;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/5
 */
public enum ErrorMap {

    OK(0, "成功"),
    ERROR(-1, "系统错误"),
    NOT_LOGIN(-1001, "未登录"),
    INVALID_CODE(-1002, "验证码错误"),
    EXPIRED_LINK(-1003, "链接已过期"),
    EMAIL_NOT_FOUND(-1004, "邮箱未注册"),
    INVALID_PASSWORD(-1005, "密码错误"),
    INVALID_USER_TYPE(-1006, "账户类型错误"),

    ;

    private Integer code;
    private String message;

    ErrorMap(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
