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
}
