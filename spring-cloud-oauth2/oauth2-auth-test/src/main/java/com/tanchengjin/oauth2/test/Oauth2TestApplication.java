package com.tanchengjin.oauth2.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.tanchengjin.*.feign")
public class Oauth2TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(Oauth2TestApplication.class, args);
    }
}
