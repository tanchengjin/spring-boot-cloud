//package com.tanchengjin.gateway.filter.factory;
//
//import com.tanchengjin.gateway.filter.HeaderTokenGatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.stereotype.Component;
//
///**
// * 局部过滤器实现步骤
// * 1.实现GatewayFilter接口
// * 2.加入过滤器工厂，并将工厂注册到Spring 容器中
// * 3.配置文件中配置路由规则
// * 配置局部过滤器的gateway工厂
// */
//@Component
//public class HeaderTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
//    @Override
//    public GatewayFilter apply(Object config) {
//        return new HeaderTokenGatewayFilter();
//    }
//}
