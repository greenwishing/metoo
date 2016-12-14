package com.metoo.web.controller;

import com.metoo.core.domain.merchant.Feature;
import com.metoo.core.domain.merchant.MerchantBusinessType;
import com.metoo.core.domain.product.ProductCategory;
import com.metoo.dto.merchant.MerchantDTO;
import com.metoo.dto.product.ProductCategoryDTO;
import com.metoo.dto.product.ProductDTO;
import com.metoo.exception.MetooException;
import com.metoo.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

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
        List<ProductDTO> productDTOs = merchantService.loadMerchantProducts(id, merchantDTO.getBusinessType().getProductClass());
        model.put("productDTOs", productDTOs);
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
        return new ModelAndView("redirect:list");
    }

    @RequestMapping("remove")
    public ModelAndView deleteMerchant(@RequestParam Long id) {
        merchantService.removeMerchantById(id);
        return new ModelAndView("redirect:list");
    }

    @RequestMapping("addProduct")
    public ModelAndView productAddForm(@RequestParam Long merchantId, ModelMap model) {
        MerchantDTO merchantDTO = merchantService.loadById(merchantId);
        ProductDTO productDTO = new ProductDTO();
        productDTO.setMerchant(merchantDTO);
        model.put("productDTO", productDTO);
        List<ProductCategoryDTO> categoryDTOs = merchantService.loadProductCategories(merchantId);
        model.put("categoryDTOs", categoryDTOs);
        return new ModelAndView("admin/product_form");
    }

    @RequestMapping("editProduct")
    public ModelAndView productEditForm(@RequestParam Long id, ModelMap model) {
        ProductDTO productDTO = merchantService.loadProductById(id);
        model.put("productDTO", productDTO);
        Long merchantId = productDTO.getMerchant().getId();
        List<ProductCategoryDTO> categoryDTOs = merchantService.loadProductCategories(merchantId);
        model.put("categoryDTOs", categoryDTOs);
        return new ModelAndView("admin/product_form");
    }

    @RequestMapping("saveProduct")
    public ModelAndView saveProduct(@ModelAttribute ProductDTO productDTO) {
        merchantService.saveOrUpdateProduct(productDTO);
        return new ModelAndView(new MappingJackson2JsonView(), "success", true);
    }

    @RequestMapping("removeProduct")
    public ModelAndView removeProduct(@RequestParam Long id) {
        merchantService.removeProductById(id);
        return null;
    }


}
