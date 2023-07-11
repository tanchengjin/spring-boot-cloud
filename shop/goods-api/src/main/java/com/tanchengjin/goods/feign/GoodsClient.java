package com.tanchengjin.goods.feign;

import com.tanchengjin.goods.feign.client.GoodsClientImpl;
import com.tanchengjin.util.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "shop-goods", fallback = GoodsClientImpl.class)
public interface GoodsClient {
    @GetMapping(value = "/goods")
    public ServerResponse getGoodsList();
}
