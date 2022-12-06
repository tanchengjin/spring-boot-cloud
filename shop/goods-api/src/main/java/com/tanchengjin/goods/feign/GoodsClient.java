package com.tanchengjin.goods.feign;

import com.tanchengjin.util.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "shop-goods")
public interface GoodsClient {
    @GetMapping(value = "/goods")
    public ServerResponse getGoodsList();
}
