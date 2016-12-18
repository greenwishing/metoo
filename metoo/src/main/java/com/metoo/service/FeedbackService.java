package com.metoo.service;

import com.metoo.dto.feedback.FeedbackDTO;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/18
 */
public interface FeedbackService {
    List<FeedbackDTO> loadFeedbackList();

    FeedbackDTO loadFeedbackById(Long id);

    void saveFeedback(FeedbackDTO feedbackDTO);
}
