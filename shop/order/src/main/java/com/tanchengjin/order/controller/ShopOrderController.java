package com.tanchengjin.order.controller;

import com.tanchengjin.goods.feign.GoodsClient;
import com.tanchengjin.util.ServerResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@RequestMapping(value = "/order")
@RestController
public class ShopOrderController {
    private final GoodsClient goodsClient;

    public ShopOrderController(GoodsClient goodsClient) {
        this.goodsClient = goodsClient;
    }


    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ServerResponse list() {
        return ServerResponse.responseWithSuccess("1");
    }

    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    public ServerResponse create() {
        ServerResponse goodsList = goodsClient.getGoodsList();
        return ServerResponse.responseWithSuccess("订单创建成功!");
    }
}
