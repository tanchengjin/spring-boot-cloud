package com.tanchengjin.cms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@ConfigurationProperties(prefix = "app")
@Component
@Data
public class App {
    private List<String> exclude;
}
