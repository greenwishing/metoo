package com.metoo.web.controller;

import com.metoo.dto.feedback.FeedbackDTO;
import com.metoo.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/10
 */
@Controller
@RequestMapping("/merchant/feedback")
public class MerchantFeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping("list")
    public ModelAndView list() {
        List<FeedbackDTO> feedbackDTOs = feedbackService.loadFeedbackList();
        return new ModelAndView("merchant/feedback_list", "feedbackDTOs", feedbackDTOs);
    }

    @RequestMapping("detail")
    public ModelAndView detail(@RequestParam Long id) {
        FeedbackDTO feedbackDTO = feedbackService.loadFeedbackById(id);
        return new ModelAndView("merchant/feedback_detail", "feedbackDTO", feedbackDTO);
    }

}
