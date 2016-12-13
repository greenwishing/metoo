package com.metoo.core.domain.merchant;

import com.metoo.core.domain.order.FoodOrder;
import com.metoo.core.domain.order.HotelOrder;
import com.metoo.core.domain.order.SceneryOrder;
import com.metoo.core.domain.product.Food;
import com.metoo.core.domain.product.Hotel;
import com.metoo.core.domain.product.Scenery;

/**
 * 业务类型
 *
 * User: Zhang xiaomei
 * Date: 2016/12/6
 */
public enum MerchantBusinessType {
    SCENERY("景点", Scenery.class, SceneryOrder.class),
    HOTEL("酒店", Hotel.class, HotelOrder.class),
    FOOD("美食", Food.class, FoodOrder.class),
    ;

    private String label;
    private Class productClass;
    private Class orderClass;

    MerchantBusinessType(String label, Class productClass, Class orderClass) {
        this.label = label;
        this.productClass = productClass;
        this.orderClass = orderClass;
    }

    public String getValue() {
        return name();
    }

    public String getLabel() {
        return label;
    }

    public Class getProductClass() {
        return productClass;
    }

    public Class getOrderClass() {
        return orderClass;
    }
}
