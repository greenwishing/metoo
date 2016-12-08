package com.metoo.core.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/7
 */
public interface ProductRepository <T extends Product> extends JpaRepository<Product, Long> {
}
