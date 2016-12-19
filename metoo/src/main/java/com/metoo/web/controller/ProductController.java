package com.metoo.web.controller;

import com.metoo.dto.merchant.MerchantDTO;
import com.metoo.dto.product.ProductCategoryDTO;
import com.metoo.dto.product.ProductDTO;
import com.metoo.service.MerchantService;
import com.metoo.service.ProductService;
import com.metoo.web.security.SecurityHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/12
 */
@Controller
@RequestMapping("/merchant/product")
public class ProductController {

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ProductService productService;

    @RequestMapping("list")
    public ModelAndView list(ModelMap model) {
        Long merchantId = SecurityHolder.get().getMerchantId();
        List<ProductDTO> productDTOs = productService.loadProducts(merchantId);
        model.put("productDTOs", productDTOs);
        return new ModelAndView("merchant/product_list", model);
    }

    @RequestMapping("add")
    public ModelAndView productAddForm(ModelMap model) {
        Long merchantId = SecurityHolder.get().getMerchantId();
        MerchantDTO merchantDTO = merchantService.loadById(merchantId);
        ProductDTO productDTO = new ProductDTO();
        productDTO.setMerchant(merchantDTO);
        model.put("productDTO", productDTO);
        List<ProductCategoryDTO> categoryDTOs = productService.loadProductCategories(merchantId);
        model.put("categoryDTOs", categoryDTOs);
        return new ModelAndView("merchant/product_form");
    }

    @RequestMapping("edit")
    public ModelAndView productEditForm(@RequestParam Long id, ModelMap model) {
        ProductDTO productDTO = productService.loadProductById(id);
        model.put("productDTO", productDTO);
        Long merchantId = SecurityHolder.get().getMerchantId();
        List<ProductCategoryDTO> categoryDTOs = productService.loadProductCategories(merchantId);
        model.put("categoryDTOs", categoryDTOs);
        return new ModelAndView("merchant/product_form");
    }

    @RequestMapping("save")
    public ModelAndView saveProduct(@ModelAttribute ProductDTO productDTO) {
        productService.saveOrUpdateProduct(productDTO);
        return new ModelAndView(new MappingJackson2JsonView(), "success", true);
    }

    @RequestMapping("toggle")
    public ModelAndView removeProduct(@RequestParam Long id) {
        productService.toggleProductStatus(id);
        return new ModelAndView(new MappingJackson2JsonView(), "success", true);
    }

    @RequestMapping("category")
    public ModelAndView productCategory(ModelMap model) {
        Long merchantId = SecurityHolder.get().getMerchantId();
        List<ProductCategoryDTO> productCategoryDTOs = productService.loadProductCategories(merchantId);
        model.put("merchantId", merchantId);
        model.put("productCategoryDTOs", productCategoryDTOs);
        return new ModelAndView("merchant/product_category_list");
    }

    @RequestMapping("addCategory")
    public ModelAndView productCategoryAddForm(ModelMap model) {
        Long merchantId = SecurityHolder.get().getMerchantId();
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
        productCategoryDTO.getMerchant().setId(merchantId);
        model.put("productCategoryDTO", productCategoryDTO);
        return new ModelAndView("merchant/product_category_form");
    }

    @RequestMapping("editCategory")
    public ModelAndView productCategoryEditForm(@RequestParam Long id, ModelMap model) {
        ProductCategoryDTO productCategoryDTO = productService.loadProductCategoryById(id);
        model.put("productCategoryDTO", productCategoryDTO);
        return new ModelAndView("merchant/product_category_form");
    }

    @RequestMapping("saveCategory")
    public ModelAndView saveProductCategory(@ModelAttribute ProductCategoryDTO productCategoryDTO) {
        productService.saveOrUpdateProductCategory(productCategoryDTO);
        return new ModelAndView(new MappingJackson2JsonView(), "success", true);
    }

    @RequestMapping("toggleCategory")
    public ModelAndView toggleProductCategoryStatus(@RequestParam Long id) {
        productService.toggleProductCategoryStatus(id);
        return new ModelAndView(new MappingJackson2JsonView(), "success", true);
    }


}
