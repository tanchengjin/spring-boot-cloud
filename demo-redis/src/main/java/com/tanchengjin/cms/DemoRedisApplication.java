package com.tanchengjin.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class DemoRedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoRedisApplication.class, args);
    }
}
