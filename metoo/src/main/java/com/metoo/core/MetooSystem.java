package com.metoo.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * User: Zhang xiaomei
 * Date: 2016/11/23
 */
@Component
@ConfigurationProperties(prefix = "metoo")
public class MetooSystem {

    private String webappName;
    private String fileuploadLocation;

    private String loginProcessesUrl;
    private String loginFailureUrl;

    private String adminLoginSuccessUrl;
    private String managerLoginSuccessUrl;
    private String customLoginSuccessUrl;

    private String logoutProcessesUrl;
    private String logoutSuccessUrl;

    public String getWebappName() {
        return webappName;
    }

    public void setWebappName(String webappName) {
        this.webappName = webappName;
    }

    public String getFileuploadLocation() {
        return fileuploadLocation;
    }

    public void setFileuploadLocation(String fileuploadLocation) {
        this.fileuploadLocation = fileuploadLocation;
    }

    public String getLoginProcessesUrl() {
        return loginProcessesUrl;
    }

    public void setLoginProcessesUrl(String loginProcessesUrl) {
        this.loginProcessesUrl = loginProcessesUrl;
    }

    public String getAdminLoginSuccessUrl() {
        return adminLoginSuccessUrl;
    }

    public void setAdminLoginSuccessUrl(String adminLoginSuccessUrl) {
        this.adminLoginSuccessUrl = adminLoginSuccessUrl;
    }

    public String getManagerLoginSuccessUrl() {
        return managerLoginSuccessUrl;
    }

    public void setManagerLoginSuccessUrl(String managerLoginSuccessUrl) {
        this.managerLoginSuccessUrl = managerLoginSuccessUrl;
    }

    public String getCustomLoginSuccessUrl() {
        return customLoginSuccessUrl;
    }

    public void setCustomLoginSuccessUrl(String customLoginSuccessUrl) {
        this.customLoginSuccessUrl = customLoginSuccessUrl;
    }

    public String getLoginFailureUrl() {
        return loginFailureUrl;
    }

    public void setLoginFailureUrl(String loginFailureUrl) {
        this.loginFailureUrl = loginFailureUrl;
    }

    public String getLogoutProcessesUrl() {
        return logoutProcessesUrl;
    }

    public void setLogoutProcessesUrl(String logoutProcessesUrl) {
        this.logoutProcessesUrl = logoutProcessesUrl;
    }

    public String getLogoutSuccessUrl() {
        return logoutSuccessUrl;
    }

    public void setLogoutSuccessUrl(String logoutSuccessUrl) {
        this.logoutSuccessUrl = logoutSuccessUrl;
    }
}
