package com.metoo.core.domain.user;

/**
 * 用户类型
 *
 * User: Zhang xiaomei
 * Date: 2016/12/7
 */
public enum UserType {
    ADMINISTRATOR("系统管理员"),
    MERCHANT_MANAGER("商户管理员"),
    CUSTOMER("普通会员"),
    ;

    private String label;

    UserType(String label) {
        this.label = label;
    }

    public String getValue() {
        return name();
    }

    public String getLabel() {
        return label;
    }
}
