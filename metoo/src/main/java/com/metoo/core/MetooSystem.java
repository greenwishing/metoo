package com.metoo.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * User: Zhang xiaomei
 * Date: 2016/11/23
 */
@Component
public class MetooSystem {

    // @Value("${metoo.webapp.name}")
    @Value("metoo.webapp.name")
    private String webappName;

    public String getWebappName() {
        return webappName;
    }

    public void setWebappName(String webappName) {
        this.webappName = webappName;
    }
}
