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
    INVALID_BUSINESS_TYPE(-1007, "业务类型错误"),

    INVALID_MERCHANT_ID(-2001, "不合法的商户"),
    INVALID_PRODUCT_CATEGORY_ID(-2002, "不合法的商品分类"),
    INVALID_PRICE(-2003, "不合法的价格"),
    INVALID_MARKETING_PRICE(-2004, "不合法的市场价格"),
    INVALID_PICTURE(-2005, "不合法的图片文件"),
    INVALID_PICTURE_SIZE(-2006, "图片文件过大（不超过512KB）"),
    INVALID_PRODUCT_ID(-2007, "不合法的商品"),
    INVALID_QUANTITY(-2008, "不合法的数量"),
    INVALID_DAYS(-2009, "不合法的天数"),
    INVALID_DESCRIPTION(-2010, "不合法的描述"),

    ALREADY_IN_USE_MERCHANT(-3001, "商户正在被使用，无法删除"),
    ALREADY_IN_USE_PRODUCT(-3002, "商品正在被使用，无法删除"),
    ALREADY_IN_USE_PRODUCT_CATEGORY(-3003, "商品分类正在被使用，无法删除"),
    ALREADY_IN_USE_USER(-3003, "用户正在被使用，无法删除"),

    ALREADY_EXISTS_EMAIL(-4001, "邮箱已被使用，试试其它的吧"),
    INVALID_USER_STATUS(-4002, "操作失败，您已被加入黑名单！"),
    INVALID_EMAIL(-4003, "邮箱格式不正确，请检查"),

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
