package com.tanchengjin.user.feign;

import com.tanchengjin.util.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "shop-goods", fallback = IGoodsClientFallback.class)
public interface IGoodsClient {
    @GetMapping(value = "/goods")
    public ServerResponse list();
}
