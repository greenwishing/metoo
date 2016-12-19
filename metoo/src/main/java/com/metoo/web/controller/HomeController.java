package com.metoo.web.controller;

import com.metoo.cache.SessionCodeHolder;
import com.metoo.core.domain.merchant.MerchantBusinessType;
import com.metoo.dto.feedback.FeedbackDTO;
import com.metoo.dto.merchant.MerchantDTO;
import com.metoo.dto.order.OrderDTO;
import com.metoo.dto.product.ProductDTO;
import com.metoo.dto.user.UserDTO;
import com.metoo.exception.ErrorMap;
import com.metoo.service.FeedbackService;
import com.metoo.service.MerchantService;
import com.metoo.service.OrderService;
import com.metoo.service.ProductService;
import com.metoo.web.filter.LoginFilter;
import com.metoo.web.utils.JsonResult;
import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/12
 */
@Controller
public class HomeController {

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping({"/", "/index"})
    public ModelAndView index(ModelMap model) {
        List<MerchantDTO> merchantDTOs = merchantService.loadMerchantSaleRanking(4);
        model.put("merchantDTOs", merchantDTOs);
        return new ModelAndView("index");
    }

    @RequestMapping("/food")
    public ModelAndView food(@Nullable Integer pageNumber, ModelMap model) {
        if (pageNumber == null) pageNumber = 1;
        PageRequest pageRequest = new PageRequest(pageNumber - 1, 15);
        Page<MerchantDTO> page = merchantService.loadMerchantByPage(MerchantBusinessType.FOOD, pageRequest);
        model.put("page", page);
        return new ModelAndView("food");
    }

    @RequestMapping("/food/{id}")
    public ModelAndView food_detail(@PathVariable Long id, ModelMap model) {
        MerchantDTO merchantDTO = merchantService.loadById(id);
        model.put("merchantDTO", merchantDTO);
        List<ProductDTO> productDTOs = productService.loadProducts(id);
        model.put("productDTOs", productDTOs);
        if (productDTOs.size() > 0) {
            model.put("productDTO", productDTOs.get(0));
        }
        return new ModelAndView("food_detail");
    }

    @RequestMapping("/food/{id}/{productId}")
    public ModelAndView food_detail(@PathVariable Long id, @PathVariable Long productId, ModelMap model) {
        MerchantDTO merchantDTO = merchantService.loadById(id);
        model.put("merchantDTO", merchantDTO);
        List<ProductDTO> productDTOs = productService.loadProducts(id);
        model.put("productDTOs", productDTOs);
        if (productDTOs.size() > 0) {
            ProductDTO currentProductDTO = null;
            for (ProductDTO productDTO : productDTOs) {
                if (productDTO.getId().equals(productId)) {
                    currentProductDTO = productDTO;
                    break;
                }
            }
            model.put("productDTO", currentProductDTO);
        }
        return new ModelAndView("food_detail");
    }

    @RequestMapping("/hotel")
    public ModelAndView hotel(@Nullable Integer pageNumber, ModelMap model) {
        if (pageNumber == null) pageNumber = 1;
        PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<MerchantDTO> page = merchantService.loadMerchantByPage(MerchantBusinessType.HOTEL, pageRequest);
        model.put("page", page);
        return new ModelAndView("hotel");
    }

    @RequestMapping("/hotel/{id}")
    public ModelAndView hotel_detail(@PathVariable Long id, ModelMap model) {
        MerchantDTO merchantDTO = merchantService.loadById(id);
        model.put("merchantDTO", merchantDTO);
        List<ProductDTO> productDTOs = productService.loadProducts(id);
        model.put("productDTOs", productDTOs);
        return new ModelAndView("hotel_detail");
    }

    @RequestMapping("/scenery")
    public ModelAndView scenery(@Nullable Integer pageNumber, ModelMap model) {
        if (pageNumber == null) pageNumber = 1;
        PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<MerchantDTO> page = merchantService.loadMerchantByPage(MerchantBusinessType.SCENERY, pageRequest);
        model.put("page", page);
        return new ModelAndView("scenery");
    }

    @RequestMapping("/scenery/{id}")
    public ModelAndView scenery_detail(@PathVariable Long id, ModelMap model) {
        MerchantDTO merchantDTO = merchantService.loadById(id);
        model.put("merchantDTO", merchantDTO);
        List<ProductDTO> productDTOs = productService.loadProducts(id);
        model.put("productDTOs", productDTOs);
        return new ModelAndView("scenery_detail");
    }

    @RequestMapping("/order/{id}")
    public ModelAndView order(@PathVariable Long id, ModelMap model) {
        ProductDTO productDTO = productService.loadProductById(id);
        model.put("productDTO", productDTO);
        return new ModelAndView("order");
    }

    @RequestMapping("/order/saveOrder")
    public ModelAndView saveOrder(@ModelAttribute OrderDTO orderDTO) {
        Long orderId = orderService.saveOrder(orderDTO);
        return new ModelAndView("redirect:/detail/" + orderId);
    }

    @RequestMapping("/detail/{id}")
    public ModelAndView saveOrder(@PathVariable Long id) {
        OrderDTO orderDTO = orderService.loadOrderById(id);
        return new ModelAndView("order_detail", "orderDTO", orderDTO);
    }

    @RequestMapping("/orders")
    public ModelAndView saveOrder(HttpSession session, HttpServletResponse response) throws IOException {
        UserDTO userDTO = (UserDTO) session.getAttribute(LoginFilter.METOO_USER_SESSION_KEY);
        if (userDTO == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }
        List<OrderDTO> orderDTOs = orderService.loadOrderByUserId(userDTO.getId());
        return new ModelAndView("order_list", "orderDTOs", orderDTOs);
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public ModelAndView feedback_form(ModelMap model) {
        model.put("businessTypes", MerchantBusinessType.values());
        return new ModelAndView("feedback");
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public ModelAndView saveFeedback(@ModelAttribute FeedbackDTO feedbackDTO, HttpSession session) {
        MerchantBusinessType businessType = feedbackDTO.getBusinessType();
        if (businessType == null) {
            return JsonResult.error(ErrorMap.INVALID_BUSINESS_TYPE);
        }
        String description = feedbackDTO.getDescription();
        if (!StringUtils.hasText(description)) {
            return JsonResult.error(ErrorMap.INVALID_DESCRIPTION);
        } else {
            if (description.length() < 10 || description.length() > 200) {
                return JsonResult.error(ErrorMap.INVALID_DESCRIPTION);
            }
        }
        String username = feedbackDTO.getUsername();
        String telephone = feedbackDTO.getTelephone();
        String email = feedbackDTO.getEmail();
        if (!StringUtils.hasText(username)) {
            return JsonResult.error("请留下您的姓名");
        }
        if (!StringUtils.hasText(telephone) && !StringUtils.hasText(email)) {
            return JsonResult.error("联系电话及邮箱请选填一项");
        }
        String code = feedbackDTO.getCode();
        if (!StringUtils.hasLength(code)) {
            return JsonResult.error("请输入验证码");
        }
        String cachedCode = SessionCodeHolder.get(session.getId());
        if (!code.equalsIgnoreCase(cachedCode)) {
            return JsonResult.error("验证码输入不正确");
        }
        SessionCodeHolder.remove(session.getId());
        feedbackService.saveFeedback(feedbackDTO);
        ModelMap model = new ModelMap();
        model.put("success", true);
        model.put("message", "反馈提交成功，米途感谢您的支持！");
        return new ModelAndView(new MappingJackson2JsonView(), model);
    }
}
