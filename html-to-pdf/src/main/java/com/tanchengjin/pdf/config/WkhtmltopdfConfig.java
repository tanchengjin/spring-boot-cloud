package com.tanchengjin.pdf.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author TanChengjin
 * @version v1.0.0
 * @email 18865477815@163.com
 */
@Configuration
@ConfigurationProperties(prefix = "app.wkhtmltopdf")
public class WkhtmltopdfConfig {
    private String exec;
    private String templateDir;
    private String templateDebug;

    private String cacheDir;


    public String getExec() {
        return exec;
    }

    public void setExec(String exec) {
        this.exec = exec;
    }

    public String getTemplateDir() {
        return templateDir;
    }

    public void setTemplateDir(String templateDir) {
        this.templateDir = templateDir;
    }

    public String getTemplateDebug() {
        return templateDebug;
    }

    public void setTemplateDebug(String templateDebug) {
        this.templateDebug = templateDebug;
    }

    public String getCacheDir() {
        return cacheDir;
    }

    public void setCacheDir(String cacheDir) {
        this.cacheDir = cacheDir;
    }
}
