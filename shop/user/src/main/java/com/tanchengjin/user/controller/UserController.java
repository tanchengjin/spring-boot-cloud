package com.tanchengjin.user.controller;

import com.tanchengjin.user.feign.IGoodsClient;
import com.tanchengjin.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private IGoodsClient goodsClient;

    @GetMapping("/test")
    public ServerResponse test() {
        ServerResponse list = goodsClient.list();
        return ServerResponse.responseWithSuccess();
    }
}
