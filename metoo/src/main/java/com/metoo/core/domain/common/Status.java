package com.metoo.core.domain.common;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/16
 */
public enum Status {
    DEACTIVATE("禁用"),
    ACTIVATED("激活"),
    ;

    private String label;

    Status(String label) {
        this.label = label;
    }

    public Integer getVal() {
        return ordinal();
    }

    public String getValue() {
        return name();
    }

    public String getLabel() {
        return label;
    }

    public Status getToggle() {
        return ACTIVATED == this ? DEACTIVATE : ACTIVATED;
    }
}
