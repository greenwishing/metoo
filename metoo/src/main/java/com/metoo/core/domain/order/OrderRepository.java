package com.metoo.core.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/7
 */
public interface OrderRepository<T extends Order> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

    List<Order> findByMerchantId(Long merchantId);

    List<Order> findByMerchantIdAndStatus(Long merchantId, OrderStatus status);
}
