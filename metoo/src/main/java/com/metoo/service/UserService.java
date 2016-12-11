package com.metoo.service;

import com.metoo.dto.user.UserDTO;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/11/24
 */
public interface UserService {
    List<UserDTO> loadUsers();

    void saveUser(UserDTO userDTO);

    UserDTO loadUserById(Long id);

    void saveOrUpdateUser(UserDTO userDTO);

    void removeUserById(Long id);

    UserDTO loadUserByEmail(String email);

    void changePassword(String email, String password);
}
