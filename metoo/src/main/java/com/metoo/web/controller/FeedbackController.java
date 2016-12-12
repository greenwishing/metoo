package com.metoo.web.controller;

import com.metoo.dto.feedback.FeedbackDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/10
 */
@Controller
public class FeedbackController {

    @RequestMapping("/admin/feedback/list")
    public ModelAndView list() {
        return new ModelAndView("feedback_list");
    }

    @RequestMapping("/admin/feedback/detail")
    public ModelAndView detail(@RequestParam Long id) {
        return new ModelAndView("feedback_form");
    }

}
