package com.metoo.web.controller;

import com.metoo.dto.feedback.FeedbackDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/12
 */
@Controller
public class HomePageController {

    @RequestMapping("/food")
    public ModelAndView food() {
        return new ModelAndView("food");
    }

    @RequestMapping("/food/{id}")
    public ModelAndView food_detail(@PathVariable Long id) {
        return new ModelAndView("food_detail");
    }

    @RequestMapping("/hotel")
    public ModelAndView hotel() {
        return new ModelAndView("hotel");
    }

    @RequestMapping("/hotel/{id}")
    public ModelAndView hotel_detail(@PathVariable Long id) {
        return new ModelAndView("hotel_detail");
    }

    @RequestMapping("/scenery")
    public ModelAndView scenery() {
        return new ModelAndView("scenery");
    }

    @RequestMapping("/scenery/{id}")
    public ModelAndView scenery_detail(@PathVariable Long id) {
        return new ModelAndView("scenery_detail");
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public ModelAndView feedback_form() {
        return new ModelAndView("feedback");
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public ModelAndView save_feedback(FeedbackDTO feedbackDTO) {
        return null;
    }
}
