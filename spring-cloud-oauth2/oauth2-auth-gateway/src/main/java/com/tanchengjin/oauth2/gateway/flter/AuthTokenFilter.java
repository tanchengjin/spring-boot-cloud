package com.tanchengjin.oauth2.gateway.flter;

import com.nimbusds.jose.JWSObject;
import com.tanchengjin.auth.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;

/**
 * 将登录用户的JWT转化成用户信息的全局过滤器
 */
@Component
public class AuthTokenFilter implements GlobalFilter, Ordered {
    private final static Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (token == null || token.length() <= 0) {
            return chain.filter(exchange);
        }
        try {
            //从token中解析用户信息并添加到到Header中
            String realToken = token.replace("Bearer ", "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            String userStr = jwsObject.getPayload().toString();
            logger.info("Auth Token info [{}]", userStr);
            ServerHttpRequest request = exchange.getRequest().mutate().header(Constants.HeaderUserKey, userStr).build();
            exchange = exchange.mutate().request(request).build();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
