package com.metoo.core.domain.product;

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

    /**
     * 是否有早餐
     */
    @Column(name = "breakfast")
    private Boolean breakfast = false;

    /**
     * 是否有窗
     */
    @Column(name = "window")
    private Boolean window = false;
}
