package com.metoo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/10
 */
@Controller
public class FoodController {

    @RequestMapping("/admin/food")
    public ModelAndView list() {
        return new ModelAndView("food_list");
    }

    @RequestMapping("/admin/food/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        return new ModelAndView("food_form");
    }

    @RequestMapping("/food")
    public ModelAndView food() {
        return new ModelAndView("food");
    }

    @RequestMapping("/food/{id}")
    public ModelAndView food_detail(@PathVariable Long id) {
        return new ModelAndView("food_detail");
    }

}
