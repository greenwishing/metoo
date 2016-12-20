package com.metoo.web.controller;

import com.metoo.core.domain.merchant.Feature;
import com.metoo.core.domain.merchant.MerchantBusinessType;
import com.metoo.dto.merchant.MerchantDTO;
import com.metoo.service.MerchantService;
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
 * Date: 2016/12/12
 */
@Controller
@RequestMapping("/admin/merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @RequestMapping("list")
    public ModelAndView list(ModelMap model) {
        List<MerchantDTO> merchantDTOs = merchantService.loadAll();
        model.put("merchantDTOs", merchantDTOs);
        return new ModelAndView("admin/merchant_list", model);
    }

    @RequestMapping("detail")
    public ModelAndView detail(@RequestParam Long id, ModelMap model) {
        MerchantDTO merchantDTO = merchantService.loadById(id);
        model.put("merchantDTO", merchantDTO);
        return new ModelAndView("admin/merchant_detail", model);
    }

    @RequestMapping("add")
    public ModelAndView add(ModelMap model) {
        model.put("merchantDTO", new MerchantDTO());
        model.put("businessTypes", MerchantBusinessType.values());
        model.put("features", Feature.values());
        return new ModelAndView("admin/merchant_form", model);
    }

    @RequestMapping("edit")
    public ModelAndView edit(@RequestParam Long id, ModelMap model) {
        MerchantDTO merchantDTO = merchantService.loadById(id);
        model.put("merchantDTO", merchantDTO);
        model.put("businessTypes", MerchantBusinessType.values());
        model.put("features", Feature.values());
        return new ModelAndView("admin/merchant_form", model);
    }

    @RequestMapping("save")
    public ModelAndView save(@ModelAttribute MerchantDTO merchantDTO) {
        merchantService.saveOrUpdateMerchant(merchantDTO);
        return JsonResult.success();
    }

    @RequestMapping("toggle")
    public ModelAndView toggleMerchantStatus(@RequestParam Long id) {
        merchantService.toggleMerchantStatus(id);
        return JsonResult.success();
    }

}
