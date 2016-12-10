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
public class HotelController {

    @RequestMapping("/admin/hotel")
    public ModelAndView list() {
        return new ModelAndView("hotel_list");
    }

    @RequestMapping("/admin/hotel/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        return new ModelAndView("hotel_form");
    }

    @RequestMapping("/hotel")
    public ModelAndView hotel() {
        return new ModelAndView("hotel");
    }

    @RequestMapping("/hotel/{id}")
    public ModelAndView hotel_detail(@PathVariable Long id) {
        return new ModelAndView("hotel_detail");
    }

}
