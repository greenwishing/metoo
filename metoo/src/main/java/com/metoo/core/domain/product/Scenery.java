package com.metoo.core.domain.product;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/6
 */
@Entity
@DiscriminatorValue("Scenery")
public class Scenery extends Product {
}
