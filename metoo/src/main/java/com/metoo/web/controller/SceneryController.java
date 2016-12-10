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
public class SceneryController {

    @RequestMapping("/admin/scenery")
    public ModelAndView list() {
        return new ModelAndView("scenery_list");
    }

    @RequestMapping("/admin/scenery/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        return new ModelAndView("scenery_form");
    }

    @RequestMapping("/scenery")
    public ModelAndView scenery() {
        return new ModelAndView("scenery");
    }

    @RequestMapping("/scenery/{id}")
    public ModelAndView scenery_detail(@PathVariable Long id) {
        return new ModelAndView("scenery_detail");
    }

}
