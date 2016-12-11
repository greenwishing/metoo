package com.metoo.web.controller;

import com.metoo.core.domain.order.OrderStatus;
import com.metoo.dto.order.OrderDTO;
import com.metoo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/10
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/admin/order")
    public ModelAndView list(OrderStatus status, ModelMap model) {
        List<OrderDTO> orderDTOs = orderService.loadOrders(status);
        model.put("statusList", OrderStatus.values());
        model.put("orderDTOs", orderDTOs);
        return new ModelAndView("admin/order_list");
    }

    @RequestMapping("/admin/order/{id}")
    public ModelAndView detail(@PathVariable Long id) {
        return new ModelAndView("admin/order_detail");
    }

}
