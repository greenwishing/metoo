package com.metoo.web.controller;

import com.metoo.cache.SessionCodeHolder;
import com.metoo.core.domain.common.Status;
import com.metoo.core.domain.merchant.MerchantBusinessType;
import com.metoo.core.domain.order.OrderStatus;
import com.metoo.dto.feedback.FeedbackDTO;
import com.metoo.dto.merchant.MerchantDTO;
import com.metoo.dto.order.OrderDTO;
import com.metoo.dto.product.ProductDTO;
import com.metoo.dto.user.UserDTO;
import com.metoo.exception.ErrorMap;
import com.metoo.service.*;
import com.metoo.web.filter.LoginFilter;
import com.metoo.web.security.SecurityHolder;
import com.metoo.web.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    @Autowired
    private UserService userService;

    @RequestMapping({"/", "/index"})
    public ModelAndView index(ModelMap model) {
        List<MerchantDTO> merchantDTOs = merchantService.loadTop5Merchants();
        model.put("merchantDTOs", merchantDTOs);
        return new ModelAndView("index");
    }

    @RequestMapping("/food")
    public ModelAndView food(ModelMap model) {
        List<MerchantDTO> merchantDTOs = merchantService.loadMerchantByBusinessType(MerchantBusinessType.FOOD);
        model.put("merchantDTOs", merchantDTOs);
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
    public ModelAndView hotel(ModelMap model) {
        List<MerchantDTO> merchantDTOs = merchantService.loadMerchantByBusinessType(MerchantBusinessType.HOTEL);
        model.put("merchantDTOs", merchantDTOs);
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
    public ModelAndView scenery(ModelMap model) {
        List<MerchantDTO> merchantDTOs = merchantService.loadMerchantByBusinessType(MerchantBusinessType.SCENERY);
        model.put("merchantDTOs", merchantDTOs);
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

    @RequestMapping("/order/save")
    public ModelAndView saveOrder(@ModelAttribute OrderDTO orderDTO) {
        Long orderId = orderService.saveOrder(orderDTO);
        return new ModelAndView("redirect:/detail/" + orderId);
    }

    @RequestMapping("/order/cancel")
    public ModelAndView cancelOrder(Long id) {
        orderService.changeOrderStatus(id, OrderStatus.CANCELED);
        return JsonResult.success();
    }

    @RequestMapping("/detail/{id}")
    public ModelAndView saveOrder(@PathVariable Long id) {
        UserDTO user = SecurityHolder.get();
        if (user == null || user.getId() == null) {
            return new ModelAndView("redirect:/login?redirectUrl=/detail/" + id);
        }
        OrderDTO orderDTO = orderService.loadOrderById(id);
        if (orderDTO == null) {
            return new ModelAndView("redirect:/orders");
        } else if (!user.getId().equals(orderDTO.getUser().getId())) {
            return new ModelAndView("error", "message", "无效的订单");
        }
        return new ModelAndView("order_detail", "orderDTO", orderDTO);
    }

    @RequestMapping("/orders")
    public ModelAndView saveOrder() {
        UserDTO userDTO = SecurityHolder.get();
        if (userDTO == null || userDTO.getId() == null) {
            return new ModelAndView("redirect:/login?redirectUrl=/orders");
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
        return JsonResult.success(model);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView feedback_form(MultipartFile picture) {
        if (picture == null) {
            return JsonResult.error("没有文件");
        }
        try {
            String pictureKey = merchantService.handlePictureUpload(picture);
            ModelMap model = new ModelMap();
            model.put("success", true);
            model.put("pictureKey", pictureKey);
            return JsonResult.success(model);
        } catch (Exception e) {
            return JsonResult.error("图片上传失败：" + e.getLocalizedMessage());
        }
    }

    @RequestMapping(value = "/passwordModify", method = RequestMethod.GET)
    public ModelAndView passwordModifyForm() {
        UserDTO userDTO = SecurityHolder.get();
        if (userDTO == null || userDTO.getId() == null) {
            return JsonResult.error("页面已过期，请刷新页面再试");
        }
        return new ModelAndView("password_modify", "userDTO", userDTO);
    }

    @RequestMapping(value = "/passwordModify", method = RequestMethod.POST)
    public ModelAndView passwordModify(HttpServletRequest request, @ModelAttribute UserDTO userDTO) {
        Long id = userDTO.getId();
        if (id == null) {
            return JsonResult.error(ErrorMap.NOT_LOGIN);
        }
        if (Status.DEACTIVATE == userDTO.getStatus()) {
            return JsonResult.error(ErrorMap.INVALID_USER_STATUS);
        }
        String password = userDTO.getPassword();
        if (!StringUtils.hasLength(password)) {
            return JsonResult.error("请输入密码");
        }
        String confirmPassword = userDTO.getConfirmPassword();
        if (!password.equals(confirmPassword)) {
            return JsonResult.error("两次输入密码不一致，请检查后重新输入");
        }
        String code = userDTO.getCode();
        HttpSession session = request.getSession();
        String cachedCode = SessionCodeHolder.get(session.getId());
        if (!code.equalsIgnoreCase(cachedCode)) {
            return JsonResult.error(ErrorMap.INVALID_CODE);
        }
        userService.modifyPassword(id, password);
        return JsonResult.success("message", "密码修改成功，下次登录请使用新密码");
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public ModelAndView userInfoForm() {
        UserDTO userDTO = SecurityHolder.get();
        if (userDTO == null || userDTO.getId() == null) {
            return JsonResult.error("页面已过期，请刷新页面再试");
        }
        return new ModelAndView("user_edit", "userDTO", userDTO);
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.POST)
    public ModelAndView userInfo(@ModelAttribute UserDTO userDTO, HttpSession session) {
        Long id = userDTO.getId();
        if (id == null) {
            return JsonResult.error(ErrorMap.NOT_LOGIN);
        }
        if (Status.DEACTIVATE == userDTO.getStatus()) {
            return JsonResult.error(ErrorMap.INVALID_USER_STATUS);
        }
        userDTO = userService.saveUserInfo(userDTO);
        session.setAttribute(LoginFilter.METOO_USER_SESSION_KEY, userDTO);
        return JsonResult.success("message", "信息已修改");
    }
}
