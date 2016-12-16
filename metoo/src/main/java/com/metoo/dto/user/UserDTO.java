package com.metoo.dto.user;

import com.metoo.core.domain.user.User;
import com.metoo.core.domain.user.UserType;
import com.metoo.dto.StatefulDTO;
import com.metoo.utils.JodaUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/11/24
 */
public class UserDTO extends StatefulDTO {

    private String email;
    private UserType type;
    private String creationTime;
    private String username;
    private String password;

    private String confirmPassword;
    private String code;

    public UserDTO() {
    }

    public UserDTO(User user) {
        super(user);
        this.email = user.getEmail();
        this.type = user.getType();
        this.creationTime = JodaUtils.dateTimeToString(user.getCreationTime());
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public static List<UserDTO> toDTOs(List<User> users) {
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO(user);
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
