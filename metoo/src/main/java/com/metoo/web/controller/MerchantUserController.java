package com.metoo.web.controller;

import com.metoo.core.domain.user.UserType;
import com.metoo.dto.merchant.MerchantUserDTO;
import com.metoo.dto.user.UserDTO;
import com.metoo.service.MerchantService;
import com.metoo.service.UserService;
import com.metoo.web.security.SecurityHolder;
import com.metoo.web.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/11/23
 */
@Controller
@RequestMapping("/merchant/user")
public class MerchantUserController {

    @Autowired
    private MerchantService merchantService;

    @RequestMapping("list")
    public ModelAndView list() {
        Long merchantId = SecurityHolder.get().getMerchantId();
        List<MerchantUserDTO> merchantUserDTOs = merchantService.loadByMerchantId(merchantId);
        return new ModelAndView("merchant/merchant_user_list", "merchantUserDTOs", merchantUserDTOs);
    }

    @RequestMapping("toggle")
    public ModelAndView toggleUserStatus(@RequestParam Long id) {
        merchantService.toggleMerchantUserStatus(id);
        return JsonResult.success();
    }

}
