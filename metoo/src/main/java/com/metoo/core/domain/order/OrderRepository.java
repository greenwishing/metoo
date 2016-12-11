package com.metoo.core.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/7
 */
public interface OrderRepository<T extends Order> extends JpaRepository<T, Long> {

    @Query("from Order o where o.status=:status")
    List<T> loadOrders(OrderStatus status);
}
