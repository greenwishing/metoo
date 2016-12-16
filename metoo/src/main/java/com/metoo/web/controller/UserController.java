package com.metoo.web.controller;

import com.metoo.core.domain.user.UserType;
import com.metoo.dto.user.UserDTO;
import com.metoo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/11/23
 */
@Controller
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("list")
    public ModelAndView list() {
        List<UserDTO> userDTOs = userService.loadUsers();
        return new ModelAndView("admin/user_list", "userDTOs", userDTOs);
    }

    @RequestMapping("add")
    public ModelAndView add(ModelMap model) {
        model.put("userDTO", new UserDTO());
        model.put("userTypes", UserType.values());
        return new ModelAndView("admin/user_form", model);
    }

    @RequestMapping("edit")
    public ModelAndView edit(@RequestParam Long id, ModelMap model) {
        UserDTO userDTO = userService.loadUserById(id);
        model.put("userDTO", userDTO);
        model.put("userTypes", UserType.values());
        return new ModelAndView("admin/user_form", model);
    }

    @RequestMapping("save")
    public ModelAndView save(@ModelAttribute UserDTO userDTO) {
        userService.saveOrUpdateUser(userDTO);
        return new ModelAndView("redirect:list");
    }

    @RequestMapping("remove")
    public ModelAndView deleteUser(@RequestParam Long id) {
        userService.removeUserById(id);
        return new ModelAndView(new MappingJackson2JsonView(), "success", true);
    }

}
