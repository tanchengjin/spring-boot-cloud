package com.tanchengjin.oauth2.conf.security.oauth2;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@Configuration
@ConfigurationProperties("oauth")
@Data
public class OauthConfig {
    private Oauth miniprogram = new Oauth();

    @Data
    public static class Oauth {
        private String app_id = "";
        private String app_secret = "";
    }
}
