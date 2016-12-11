package com.metoo.service;

import com.metoo.core.domain.order.OrderStatus;
import com.metoo.dto.order.OrderDTO;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/11
 */
public interface OrderService {
    List<OrderDTO> loadOrders(OrderStatus status);
}
