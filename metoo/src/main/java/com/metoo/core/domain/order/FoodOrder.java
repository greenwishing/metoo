package com.metoo.core.domain.order;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * 美食订单
 * User: Zhang xiaomei
 * Date: 2016/12/6
 */
@Entity
@DiscriminatorValue("FoodOrder")
public class FoodOrder extends Order {

    /**
     * 数量
     */
    @Column(name = "quantity")
    private Integer quantity;
}
