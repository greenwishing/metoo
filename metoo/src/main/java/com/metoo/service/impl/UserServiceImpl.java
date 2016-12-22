package com.metoo.service.impl;

import com.metoo.core.domain.common.DomainUtils;
import com.metoo.core.domain.user.User;
import com.metoo.core.domain.user.UserRepository;
import com.metoo.core.domain.user.UserType;
import com.metoo.dto.user.UserDTO;
import com.metoo.exception.ErrorMap;
import com.metoo.exception.MetooFormException;
import com.metoo.service.UserService;
import com.metoo.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/11/24
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> loadUsers() {
        List<User> users = userRepository.findAll();
        return UserDTO.toDTOs(users);
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        String email = userDTO.getEmail();
        User user = new User(email);
        user.setUsername(userDTO.getUsername());
        user.setPassword(MD5Utils.encode(userDTO.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDTO loadUserById(Long id) {
        User user = userRepository.findOne(id);
        return new UserDTO(user);
    }

    @Override
    public void saveOrUpdateUser(UserDTO userDTO) {
        Long id = userDTO.getId();
        String email = userDTO.getEmail();
        if (!email.matches("^\\w+([-_.]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,6})+$")) {
            throw new MetooFormException(ErrorMap.INVALID_EMAIL);
        }
        User exists = userRepository.findByEmail(email);

        User user;
        if (id != null) {
            user = userRepository.findOne(id);
            if (exists != null && !email.equals(user.getEmail())) {
                throw new MetooFormException(ErrorMap.ALREADY_EXISTS_EMAIL);
            }
        } else {
            user = new User();
            if (exists != null) {
                throw new MetooFormException(ErrorMap.ALREADY_EXISTS_EMAIL);
            }
        }

        user.setEmail(email);
        user.setUsername(userDTO.getUsername());
        userRepository.save(user);
    }

    @Override
    public void toggleUserStatus(Long id) {
        DomainUtils.toggleStatus(userRepository, id);
    }

    @Override
    public UserDTO loadUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user == null ? null : new UserDTO(user);
    }

    @Override
    public void changePassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        user.setPassword(MD5Utils.encode(password));
        userRepository.save(user);
    }

    @Override
    public void modifyPassword(Long userId, String password) {
        User user = userRepository.findOne(userId);
        user.setPassword(MD5Utils.encode(password));
        userRepository.save(user);
    }

    @Override
    public UserDTO saveUserInfo(UserDTO userDTO) {
        Long id = userDTO.getId();
        String email = userDTO.getEmail();
        if (!email.matches("^\\w+([-_.]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,6})+$")) {
            throw new MetooFormException(ErrorMap.INVALID_EMAIL);
        }
        User exists = userRepository.findByEmail(email);
        User user = userRepository.findOne(id);
        if (exists != null && !email.equals(user.getEmail())) {
            throw new MetooFormException(ErrorMap.ALREADY_EXISTS_EMAIL);
        }

        user.setEmail(email);
        user.setUsername(userDTO.getUsername());
        userRepository.save(user);
        return new UserDTO(user);
    }
}
