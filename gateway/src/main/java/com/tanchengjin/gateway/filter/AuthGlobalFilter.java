//package com.tanchengjin.gateway.filter;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
///**
// * JWT信息转换到Header
// */
//@Component
//public class AuthGlobalFilter implements GlobalFilter, Ordered {
//    private final static Logger logger = LoggerFactory.getLogger(AuthGlobalFilter.class);
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
//        if (StringUtils.isEmpty(authorization)) {
//            return chain.filter(exchange);
//        }
//        try {
//            // 从token中解析用户信息并设置到Header中去
//            String token = authorization.replace("Bearer ", "");
////            String userStr = parse.getPayload().toString();
////            logger.info("AuthGlobalFilter.filter() user:{}", parse);
//            ServerHttpRequest request = exchange.getRequest().mutate().header("user", "user").build();
//            exchange = exchange.mutate().request(request).build();
//        } catch (Exception e) {
//            logger.info("exception => [{}]", e.getMessage());
//            e.printStackTrace();
//        }
//        return chain.filter(exchange);
//    }
//
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}