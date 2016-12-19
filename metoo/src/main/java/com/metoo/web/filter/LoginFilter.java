package com.metoo.web.filter;

import com.metoo.cache.SessionCodeHolder;
import com.metoo.core.MetooSystem;
import com.metoo.core.domain.merchant.Merchant;
import com.metoo.core.domain.user.UserType;
import com.metoo.dto.merchant.MerchantDTO;
import com.metoo.dto.user.UserDTO;
import com.metoo.exception.ErrorMap;
import com.metoo.exception.MetooLoginException;
import com.metoo.service.MerchantService;
import com.metoo.service.UserService;
import com.metoo.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/10
 */
@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
public class LoginFilter extends MetooFilter {

    @Autowired
    private UserService userService;
    @Autowired
    private MetooSystem metooSystem;
    @Autowired
    private MerchantService merchantService;

    @Override
    protected void processes(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (!request.getMethod().equals("POST")) {
            chain.doFilter(request, response);
            return;
        }

        UserDTO user = null;
        try {
            user = login(request, response);
            if (user == null) {
                return;
            }
        } catch (MetooLoginException failed) {
            loginFailure(request, response, failed);
            return;
        }

        loginSuccess(request, response, chain, user);
    }

    @Override
    protected String getProcessesUrl() {
        return metooSystem.getLoginProcessesUrl();
    }


    private UserDTO login(HttpServletRequest request, HttpServletResponse response) {
        String email = ServletRequestUtils.getStringParameter(request, "email", "");
        String password = ServletRequestUtils.getStringParameter(request, "password", "");
        String code = ServletRequestUtils.getStringParameter(request, "code", "");
        UserDTO user = userService.loadUserByEmail(email);
        if (user == null) {
            throw new MetooLoginException(ErrorMap.EMAIL_NOT_FOUND);
        }
        HttpSession session = request.getSession();
        String cachedCode = SessionCodeHolder.get(session.getId());
        if (!code.equalsIgnoreCase(cachedCode)) {
            throw new MetooLoginException(ErrorMap.INVALID_CODE);
        }
        SessionCodeHolder.remove(email);
        String md5Password = MD5Utils.encode(password);
        if (!md5Password.equals(user.getPassword())) {
            throw new MetooLoginException(ErrorMap.INVALID_PASSWORD);
        }
        return user;
    }

    private void loginSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, UserDTO user) throws IOException, ServletException {
        onLoginSuccess(request, response, user);
        chain.doFilter(request, response);
    }

    private void loginFailure(HttpServletRequest request, HttpServletResponse response, MetooLoginException failed) throws IOException, ServletException {
        String failureUrl = metooSystem.getLoginFailureUrl();
        if (failureUrl == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "登录失败：" + failed.getMessage());
        } else {
            response.sendRedirect(failureUrl + "?code=" + failed.getError().getCode());
        }
    }

    private void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, UserDTO user) throws IOException {
        request.getSession().setAttribute(METOO_USER_SESSION_KEY, user);
        UserType type = user.getType();
        String loginSuccessUrl;
        switch (type) {
            case ADMINISTRATOR:
                loginSuccessUrl = metooSystem.getAdminLoginSuccessUrl();
                break;
            case MERCHANT_MANAGER:
                Long managerId = user.getId();
                MerchantDTO merchantDTO = merchantService.loadByManagerId(managerId);
                if (merchantDTO == null) {
                    throw new MetooLoginException("管理员未关联有效的商户：" + user.getEmail());
                }
                user.update(merchantDTO.getId(), merchantDTO.getName(), merchantDTO.getBusinessType());
                loginSuccessUrl = metooSystem.getManagerLoginSuccessUrl();
                break;
            case CUSTOMER:
                loginSuccessUrl = metooSystem.getCustomLoginSuccessUrl();
                break;
            default:
                throw new MetooLoginException("不支持的用户类型：" + type.getLabel());
        }
        response.sendRedirect(loginSuccessUrl);
    }
}
