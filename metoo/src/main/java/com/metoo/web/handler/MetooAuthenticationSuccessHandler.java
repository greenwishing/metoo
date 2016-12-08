package com.metoo.web.handler;

import com.metoo.dto.UserDTO;
import com.metoo.web.security.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/7
 */
@Component
public class MetooAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        switch (principal.getType()) {
            case ADMINISTRATOR:
                response.sendRedirect("/admin/user/list");
                break;
            case CUSTOMER:
            default:
                String redirectUrl = ServletRequestUtils.getStringParameter(request, "redirectUrl");
                if (StringUtils.hasLength(redirectUrl)) {
                    response.sendRedirect(redirectUrl);
                } else {
                    response.sendRedirect("/");
                }
                break;
        }
    }
}
