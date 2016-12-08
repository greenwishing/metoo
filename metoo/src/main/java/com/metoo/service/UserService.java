package com.metoo.service;

import com.metoo.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/11/24
 */
public interface UserService extends UserDetailsService {
    List<UserDTO> loadUsers();

    void saveUser(UserDTO userDTO);

    UserDTO loadUserById(Long id);

    void saveOrUpdateUser(UserDTO userDTO);

    void removeUserById(Long id);

    UserDTO loadUserByEmail(String email);

    void changePassword(String email, String password);
}
