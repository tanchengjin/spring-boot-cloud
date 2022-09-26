package com.tanchengjin.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置登录请求转发规则
 */
@Configuration
public class RouteLocatorConfig {
    /**
     * 使用代码定义路由规则，在网关层面拦截下登录和注册接口
     *
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator loginRouteLocator(RouteLocatorBuilder builder) {
        //手动定义Gateway 路由规则需要指定id、path和uri
//        return builder.routes()
//                .route("app-authentication", r -> r.path("/imooc/e-commerce/login", "/imooc/e-commerce/register").uri("http://localhost:9001"))
//                .build();
        return builder.routes()
                .route("auth-service", r -> r.path("/imooc/test-auth").uri("http://localhost:8088"))
                .build();
    }
}
