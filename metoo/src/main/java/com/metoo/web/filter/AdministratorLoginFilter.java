package com.metoo.web.filter;

import com.metoo.core.domain.user.UserType;
import com.metoo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.annotation.WebFilter;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/10
 */
@WebFilter(filterName = "administratorLoginFilter", urlPatterns = "/*")
public class AdministratorLoginFilter extends LoginFilter {

    @Override
    protected boolean isUserType(UserType userType) {
        return UserType.ADMINISTRATOR == userType;
    }

    @Override
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Value("${metoo.administrator.login.url}")
    public void setProcessesUrl(String processesUrl) {
        this.processesUrl = processesUrl;
    }

    @Override
    @Value("${metoo.administrator.login.success.url}")
    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    @Override
    @Value("${metoo.administrator.login.failure.url}")
    public void setFailureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
    }

}
