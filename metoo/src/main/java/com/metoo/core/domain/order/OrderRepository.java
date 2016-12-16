package com.metoo.core.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/7
 */
public interface OrderRepository<T extends Order> extends JpaRepository<T, Long> {

    @Query("from Order o where o.status=:status")
    List<T> loadOrders(@Param("status") OrderStatus status);

    @Query("select count(*) from Order o where o.user.id=:userId")
    Integer checkUserUsed(@Param("userId") Long userId);
}
