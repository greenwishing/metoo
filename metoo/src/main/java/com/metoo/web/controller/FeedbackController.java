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
public class FeedbackController {

    @RequestMapping("/admin/feedback")
    public ModelAndView list() {
        return new ModelAndView("feedback_list");
    }

    @RequestMapping("/admin/feedback/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        return new ModelAndView("feedback_form");
    }

    @RequestMapping("/feedback")
    public ModelAndView feedback() {
        return new ModelAndView("feedback");
    }

}
