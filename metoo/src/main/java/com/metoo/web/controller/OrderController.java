package com.metoo.web.controller;

import com.metoo.core.domain.order.OrderStatus;
import com.metoo.dto.order.OrderDTO;
import com.metoo.service.OrderService;
import com.metoo.web.security.SecurityHolder;
import com.metoo.web.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/10
 */
@Controller
@RequestMapping("/merchant/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("list")
    public ModelAndView list(OrderStatus status, ModelMap model) {
        Long merchantId = SecurityHolder.get().getMerchantId();
        List<OrderDTO> orderDTOs = orderService.loadByMerchantId(merchantId, status);
        model.put("statusList", OrderStatus.values());
        model.put("orderDTOs", orderDTOs);
        return new ModelAndView("merchant/order_list");
    }

    @RequestMapping("detail")
    public ModelAndView detail(@RequestParam Long id) {
        OrderDTO orderDTO = orderService.loadOrderById(id);
        return new ModelAndView("merchant/order_detail", "orderDTO", orderDTO);
    }

    @RequestMapping("success")
    public ModelAndView success(@RequestParam Long id) {
        orderService.changeOrderStatus(id, OrderStatus.SUCCESS);
        return JsonResult.success();
    }

    @RequestMapping("cancel")
    public ModelAndView cancel(@RequestParam Long id) {
        orderService.changeOrderStatus(id, OrderStatus.CANCELED);
        return JsonResult.success();
    }

}
