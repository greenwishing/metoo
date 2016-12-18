package com.metoo.dto.feedback;

import com.metoo.core.domain.feedback.Feedback;
import com.metoo.core.domain.merchant.MerchantBusinessType;
import com.metoo.core.domain.user.User;
import com.metoo.dto.StatefulDTO;
import com.metoo.dto.user.UserDTO;
import com.metoo.utils.JodaUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/11
 */
public class FeedbackDTO extends StatefulDTO {

    private UserDTO user = new UserDTO();
    private String creationTime;
    private MerchantBusinessType businessType;
    private String description;
    private String username;
    private String telephone;
    private String email;

    private String code;

    public FeedbackDTO() {
        super();
    }

    public FeedbackDTO(Feedback feedback) {
        super(feedback);
        User user = feedback.getUser();
        if (user != null) {
            this.user = new UserDTO(user);
        }
        this.creationTime = JodaUtils.dateTimeToString(feedback.getCreationTime());
        this.businessType = feedback.getBusinessType();
        this.description = feedback.getDescription();
        this.username = feedback.getUsername();
        this.telephone = feedback.getTelephone();
        this.email = feedback.getEmail();
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public MerchantBusinessType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(MerchantBusinessType businessType) {
        this.businessType = businessType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static List<FeedbackDTO> toDTOs(List<Feedback> feedbackList) {
        List<FeedbackDTO> feedbackDTOs = new ArrayList<>();
        for (Feedback feedback : feedbackList) {
            FeedbackDTO feedbackDTO = new FeedbackDTO(feedback);
            feedbackDTOs.add(feedbackDTO);
        }
        return feedbackDTOs;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
