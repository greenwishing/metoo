package com.metoo.web.controller;

import com.metoo.dto.UserDTO;
import com.metoo.exception.ErrorMap;
import com.metoo.exception.MetooException;
import com.metoo.service.MailService;
import com.metoo.service.UserService;
import com.metoo.utils.MD5Utils;
import com.metoo.utils.VerifyCodeUtils;
import com.metoo.web.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Zhang xiaomei
 * Date: 2016/11/23
 */
@RestController
public class IndexController {

    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;

    private static final String SESSION_LOGIN_INFO_KEY = "USER_INFO";
    private static final Map<String, String> SESSION_CODE_CACHE = new HashMap<>();
    private static final Map<String, String> SESSION_MAIL_CACHE = new HashMap<>();

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login_form() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(HttpSession session, UserDTO userDTO, String redirectUrl) {
        String email = userDTO.getEmail();
        if (!StringUtils.hasLength(email)) {
            return JsonResult.error(ErrorMap.INVALID_CODE);
        }
        UserDTO loginUser =  userService.loadUserByEmail(email);
        if (loginUser == null) {
            return JsonResult.error("邮箱未注册，请检查您输入的邮箱是否有误");
        }
        String password = userDTO.getPassword();
        if (!StringUtils.hasLength(password)) {
            return JsonResult.error("请输入登录密码");
        }
        String code = userDTO.getCode();
        if (!StringUtils.hasLength(code)) {
            return JsonResult.error("请输入验证码");
        }
        String cachedCode = SESSION_CODE_CACHE.get(session.getId());
        if (!code.equalsIgnoreCase(cachedCode)) {
            return JsonResult.error("验证码输入不正确");
        }
        String md5Password = MD5Utils.encode(password);
        if (!md5Password.equals(loginUser.getPassword())) {
            return JsonResult.error("密码输入不正确");
        }
        session.setAttribute(SESSION_LOGIN_INFO_KEY, loginUser);
        return JsonResult.success("redirectUrl", StringUtils.hasLength(redirectUrl) ? redirectUrl : "/index");
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public ModelAndView register_form() {
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ModelAndView register(UserDTO userDTO, HttpSession session) {
        String email = userDTO.getEmail();
        if (!StringUtils.hasLength(email)) {
            return JsonResult.error("请输入邮箱");
        } else {
            if (!email.matches("^\\w+([-_.]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,6})+$")) {
                return JsonResult.error("邮箱格式不正确");
            }
            UserDTO exists = userService.loadUserByEmail(email);
            if (exists != null) {
                return JsonResult.error("邮箱已被注册，换一个试试吧");
            }
        }
        String username = userDTO.getUsername();
        if (!StringUtils.hasLength(username)) {
            return JsonResult.error("请输入用户名");
        }
        String password = userDTO.getPassword();
        if (!StringUtils.hasLength(password)) {
            return JsonResult.error("请输入登录密码");
        }
        String confirmPassword = userDTO.getConfirmPassword();
        if (!password.equals(confirmPassword)) {
            return JsonResult.error("两次输入密码不一致，请检查后重新输入");
        }
        String code = userDTO.getCode();
        if (!StringUtils.hasLength(code)) {
            return JsonResult.error("请输入验证码");
        }
        String cachedCode = SESSION_CODE_CACHE.get(session.getId());
        if (!code.equalsIgnoreCase(cachedCode)) {
            return JsonResult.error("验证码输入不正确");
        }
        userService.saveUser(userDTO);
        return JsonResult.success("redirectUrl", "/login");
    }

    @RequestMapping(value = "/forgot_password",method = RequestMethod.GET)
    public ModelAndView forgot_password_form() {
        return new ModelAndView("forgot_password");
    }

    @RequestMapping(value = "/forgot_password",method = RequestMethod.POST)
    public ModelAndView forgot_password(HttpServletRequest request, String email, String code) {
        if (!StringUtils.hasLength(code)) {
            return JsonResult.error("请输入验证码");
        }
        HttpSession session = request.getSession();
        String cachedCode = SESSION_CODE_CACHE.get(session.getId());
        if (!code.equalsIgnoreCase(cachedCode)) {
            return JsonResult.error("验证码输入不正确");
        }
        UserDTO userDTO = userService.loadUserByEmail(email);
        if (userDTO == null) {
            return JsonResult.error("邮箱未注册，请检查您输入的邮箱是否有误");
        }
        String mailTo = userDTO.getEmail();
        String hostname = "http://" + request.getServerName() + ":" + (request.getServerPort()==80 ? "" : request.getServerPort());
        String resetUrl = hostname + "/reset_password?token=" + session.getId();
        String content = "<p>尊敬的" + userDTO.getUsername() + "：</p>" +
                "<p style=\"background: #f3f3f3; padding: 5px 10px\">您提交了找回密码的申请。请点击<a href=\"" + resetUrl + "\">重新设置密码</a>。</p>" +
                "<p>如果上述链接无法正常点击，请在浏览器复制打开以下地址：</p>" +
                "<p><a href=\"" + resetUrl + "\">" + resetUrl + "</a></p>" +
                "<p style=\"color: red\">请尽快重置您的登录密码，以上链接仅在30分钟内有效。</p>";
        try {
            mailService.sendMail(mailTo, "迷途旅游：请重置您的登录密码", content);
            SESSION_MAIL_CACHE.put(session.getId(), userDTO.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error("邮件发送失败，请重试");
        }
        return null;
    }

    @RequestMapping(value = "/reset_password",method = RequestMethod.GET)
    public ModelAndView reset_password_form(String token) {
        if (token == null) {
            throw new MetooException(ErrorMap.EXPIRED_LINK);
        }
        String email = SESSION_MAIL_CACHE.get(token);
        if (email == null) {
            throw new MetooException(ErrorMap.EXPIRED_LINK);
        }
        if (!StringUtils.hasLength(token)) {
            throw new MetooException(ErrorMap.EXPIRED_LINK);
        }
        return new ModelAndView("reset_password");
    }

    @RequestMapping(value = "/reset_password",method = RequestMethod.POST)
    public ModelAndView reset_password(HttpSession session, String token, String password, String confirmPassword) {
        if (token == null) {
            return JsonResult.error(ErrorMap.EXPIRED_LINK);
        }
        String email = SESSION_MAIL_CACHE.get(token);
        if (email == null) {
            return JsonResult.error(ErrorMap.EXPIRED_LINK);
        }
        UserDTO userDTO = userService.loadUserByEmail(email);
        if (userDTO == null) {
            return JsonResult.error(ErrorMap.EXPIRED_LINK);
        }
        if (!StringUtils.hasLength(password)) {
            return JsonResult.error("请输入密码");
        }
        if (!password.equals(confirmPassword)) {
            return JsonResult.error("两次输入密码不一致，请检查后重新输入");
        }
        userService.changePassword(email, password);
        return JsonResult.success("redirectUrl", "/login");
    }

    @RequestMapping("/code")
    public ModelAndView code(HttpServletRequest request, HttpServletResponse response) {
        try {
            ServletOutputStream os = response.getOutputStream();
            String code = VerifyCodeUtils.generateVerifyCode(4);
            SESSION_CODE_CACHE.put(request.getSession().getId(), code);
            VerifyCodeUtils.outputImage(80, 34, os, code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping({"/", "/index"})
    public ModelAndView index(HttpSession session) {
        return new ModelAndView("index");
    }

    @RequestMapping("/scenery")
    public ModelAndView scenery() {
        return new ModelAndView("scenery");
    }

    @RequestMapping("/scenery/{id}")
    public ModelAndView scenery_detail(@PathVariable Integer id) {
        return new ModelAndView("scenery_detail");
    }

    @RequestMapping("/hotel")
    public ModelAndView hotel() {
        return new ModelAndView("hotel");
    }

    @RequestMapping("/hotel/{id}")
    public ModelAndView hotel_detail(@PathVariable Integer id) {
        return new ModelAndView("hotel_detail");
    }

    @RequestMapping("/food")
    public ModelAndView food() {
        return new ModelAndView("food");
    }

    @RequestMapping("/food/{id}")
    public ModelAndView food_detail(@PathVariable Integer id) {
        return new ModelAndView("food_detail");
    }

    @RequestMapping("/feedback")
    public ModelAndView feedback() {
        return new ModelAndView("feedback");
    }
}
