package com.metoo.core.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/7
 */
public interface OrderRepository<T extends Order> extends JpaRepository<T, Long> {
}
