package com.tanchengjin.pdf.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author TanChengjin
 * @version v1.0.0
 * @email 18865477815@163.com
 */
@Configuration
@ConfigurationProperties(prefix = "app")
public class App {
    /**
     * html convert pdf
     */
    private WkhtmltopdfConfig wkhtmltopdf;

    private String url;

    private String storage;

    public WkhtmltopdfConfig getWkhtmltopdf() {
        return wkhtmltopdf;
    }

    public void setWkhtmltopdf(WkhtmltopdfConfig wkhtmltopdf) {
        this.wkhtmltopdf = wkhtmltopdf;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }
}
