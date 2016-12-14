package com.metoo.core.domain.product;

import com.metoo.core.domain.merchant.Merchant;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/6
 */
@Entity
@DiscriminatorValue("Hotel")
public class Hotel extends Product {

    public Hotel() {
        super();
    }

    public Hotel(Merchant merchant) {
        super(merchant);
    }

    /**
     * 是否有早餐
     */
    @Column(name = "breakfast")
    private boolean hasBreakfast = false;

    /**
     * 是否有窗
     */
    @Column(name = "window")
    private boolean hasWindow = false;

    public void update(boolean hasBreakfast, boolean hasWindow) {
        this.hasBreakfast = hasBreakfast;
        this.hasWindow = hasWindow;
    }

    public boolean isHasBreakfast() {
        return hasBreakfast;
    }

    public boolean isHasWindow() {
        return hasWindow;
    }
}
