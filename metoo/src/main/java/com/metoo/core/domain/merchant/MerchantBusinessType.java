package com.metoo.core.domain.merchant;

/**
 * 业务类型
 *
 * User: Zhang xiaomei
 * Date: 2016/12/6
 */
public enum MerchantBusinessType {
    SCENERY("景点"),
    HOTEL("酒店"),
    FOOD("美食"),
    ;

    private String label;

    MerchantBusinessType(String label) {
        this.label = label;
    }

    public String getValue() {
        return name();
    }

    public String getLabel() {
        return label;
    }
}
