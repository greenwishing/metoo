package com.metoo.core.domain.order;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * 酒店订单
 * User: Zhang xiaomei
 * Date: 2016/12/6
 */
@Entity
@DiscriminatorValue("HotelOrder")
public class HotelOrder extends Order {

    /**
     * 房间数
     */
    @Column(name = "quantity")
    private Integer quantity;

    /**
     * 入住天数，酒店
     */
    @Column(name = "days")
    private Integer days;

    public void update(Integer quantity, Integer days) {
        this.quantity = quantity;
        this.days = days;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getDays() {
        return days;
    }
}
