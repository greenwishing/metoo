package com.metoo.core.domain.product;

import com.metoo.core.domain.merchant.Merchant;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/6
 */
@Entity
@DiscriminatorValue("Scenery")
public class Scenery extends Product {

    public Scenery() {
        super();
    }

    public Scenery(Merchant merchant) {
        super(merchant);
    }
}
