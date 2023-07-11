package com.tanchengjin.cms.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "cms", fallback = IArticleClientFallback.class)
public interface IArticleClient {
    public final String GET_ARTICLE_BY_ID = "/articles/{id}";

    @GetMapping(value = GET_ARTICLE_BY_ID)
    public Object getArticleById(@PathVariable("id") Long id);
}
