package com.metoo.core.domain.order;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/11
 */
public enum OrderStatus {
    NEW("待确认"),
    SUCCESS("成功"),
    CANCELED("已取消"),
    ;

    private String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String getValue() {
        return name();
    }

    public String getLabel() {
        return label;
    }
}
