package com.tanchengjin.oauth2.gateway.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * Token认证失败、超时等错误相应处理
 *
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@Component
public class AccessAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    private final static Logger logger = LoggerFactory.getLogger(AccessAuthenticationEntryPoint.class);

    @Override
    public Mono<Void> commence(ServerWebExchange serverWebExchange, AuthenticationException e) {

        return Mono.defer(() -> Mono.just(serverWebExchange.getResponse()))
                .flatMap(response -> {
                    logger.error(e.getMessage());
                    response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    response.getHeaders().set("Access-Control-Allow-Origin","*");
                    response.getHeaders().set("Cache-Control","no-cache");
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    String body = "{\"code\":401,\"msg\":\"token不合法或过期\"}";
                    DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
                    return response.writeWith(Mono.just(buffer))
                            .doOnError(error -> DataBufferUtils.release(buffer));
                });
    }
}
