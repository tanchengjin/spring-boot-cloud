package com.tanchengjin.oauth2.gateway.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@Configuration
public class RedisTemplateConfig {
    @Bean
    public RedisTemplate redisTemplate() {
        return new RedisTemplate();
    }
}
