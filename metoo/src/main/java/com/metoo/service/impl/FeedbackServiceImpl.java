package com.metoo.service.impl;

import com.metoo.cache.SessionCodeHolder;
import com.metoo.core.domain.feedback.Feedback;
import com.metoo.core.domain.feedback.FeedbackRepository;
import com.metoo.core.domain.merchant.MerchantBusinessType;
import com.metoo.core.domain.user.User;
import com.metoo.core.domain.user.UserRepository;
import com.metoo.dto.feedback.FeedbackDTO;
import com.metoo.service.FeedbackService;
import com.metoo.web.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/18
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<FeedbackDTO> loadFeedbackList() {
        List<Feedback> feedbackList = feedbackRepository.findAll();
        return FeedbackDTO.toDTOs(feedbackList);
    }

    @Override
    public FeedbackDTO loadFeedbackById(Long id) {
        Feedback feedback = feedbackRepository.findOne(id);
        return new FeedbackDTO(feedback);
    }

    @Override
    public void saveFeedback(FeedbackDTO feedbackDTO) {
        Long userId = feedbackDTO.getUser().getId();
        User user = null;
        if (userId != null) {
            user = userRepository.findOne(userId);
        }
        MerchantBusinessType businessType = feedbackDTO.getBusinessType();
        Feedback feedback = new Feedback(user, businessType);
        feedback.update(feedbackDTO.getDescription(), feedbackDTO.getUsername(), feedbackDTO.getTelephone(), feedbackDTO.getEmail());
        feedbackRepository.save(feedback);
    }
}
