package com.metoo.dto.feedback;

import com.metoo.core.domain.feedback.Feedback;
import com.metoo.core.domain.merchant.MerchantBusinessType;
import com.metoo.dto.user.UserDTO;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/11
 */
public class FeedbackDTO {

    private Long id;
    private UserDTO user = new UserDTO();
    private String creationTime;
    private MerchantBusinessType businessType;
    private String description;
    private String username;
    private String telephone;
    private String email;

    public FeedbackDTO() {
    }

    public FeedbackDTO(Feedback feedback) {
        this.id = feedback.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
