package com.tanchengjin.cms.feign;

import org.springframework.stereotype.Component;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@Component
public class IArticleClientFallback implements IArticleClient {
    @Override
    public Object getArticleById(Long id) {
        return null;
    }
}
