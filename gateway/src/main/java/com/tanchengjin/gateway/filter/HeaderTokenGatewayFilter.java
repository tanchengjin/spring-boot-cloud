//package com.tanchengjin.gateway.filter;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
///**
// * 校验头部token验证过滤器(局部过滤器)
// */
//public class HeaderTokenGatewayFilter implements GatewayFilter, Ordered {
//    private final static Logger logger = LoggerFactory.getLogger(HeaderTokenGatewayFilter.class);
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        //从http header中查找key value键值对
//        String token = exchange.getRequest().getHeaders().getFirst("token");
//        if ("imooc".equals(token)) {
//            return chain.filter(exchange);
//        }
//
//        logger.error("请求头错误");
//        //标记此次请求没有权限，并结束请求
//        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//        return exchange.getResponse().setComplete();
//    }
//
//    /**
//     * 优先级越高越晚被执行
//     *
//     * @return
//     */
//    @Override
//    public int getOrder() {
//        return HIGHEST_PRECEDENCE + 2;
//    }
//}
