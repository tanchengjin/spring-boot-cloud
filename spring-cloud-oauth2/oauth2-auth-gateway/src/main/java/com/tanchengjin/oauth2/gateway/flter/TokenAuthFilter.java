//package com.tanchengjin.oauth2.gateway.flter;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
///**
// * @Author TanChengjin
// * @Email 18865477815@163.com
// * @Since V1.0.0
// **/
//@Component
//public class TokenAuthFilter implements WebFilter {
//    @Override
//
//    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
//        Mono<Void> user = ReactiveSecurityContextHolder.getContext()
//                .map(SecurityContext::getAuthentication)
//                .cast(JwtAuthenticationToken.class)
//                .flatMap(authentication -> {
//                    ServerHttpRequest request = serverWebExchange.getRequest();
//                    request = request.mutate()
//                            .header("user", toJson(authentication.getPrincipal()))
//                            .build();
//
//                    ServerWebExchange newExchange = serverWebExchange.mutate().request(request).build();
//
//                    return webFilterChain.filter(newExchange);
//                });
//        return user;
//    }
//
//    private String toJson(Object o) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            return objectMapper.writeValueAsString(o);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
