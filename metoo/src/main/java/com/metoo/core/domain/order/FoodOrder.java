package com.metoo.core.domain.order;

import com.metoo.core.domain.merchant.Merchant;
import com.metoo.core.domain.product.Product;
import com.metoo.core.domain.user.User;

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

    public FoodOrder() {
    }

    public FoodOrder(Merchant merchant, User user, Product product) {
        super(merchant, user, product);
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
