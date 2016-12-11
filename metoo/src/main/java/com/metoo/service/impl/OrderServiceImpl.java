package com.metoo.service.impl;

import com.metoo.core.domain.order.Order;
import com.metoo.core.domain.order.OrderRepository;
import com.metoo.core.domain.order.OrderStatus;
import com.metoo.dto.order.OrderDTO;
import com.metoo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/11
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository<Order> orderRepository;

    @Override
    public List<OrderDTO> loadOrders(OrderStatus status) {
        List<Order> orders;
        if (status == null) {
            orders = orderRepository.findAll();
        } else {
            orders = orderRepository.loadOrders(status);
        }
        return OrderDTO.toDTOs(orders);
    }
}
