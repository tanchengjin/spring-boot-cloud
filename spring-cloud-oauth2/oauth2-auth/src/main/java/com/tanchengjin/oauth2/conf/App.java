package com.tanchengjin.oauth2.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@ConfigurationProperties(prefix = "app")
@Data
public class App {
    private List<String> excludeUrl;
}
