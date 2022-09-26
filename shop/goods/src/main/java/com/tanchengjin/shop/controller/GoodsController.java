package com.tanchengjin.shop.controller;

import com.tanchengjin.util.ServerResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @RequestMapping(value = {"", "/"})
    public ServerResponse list() {
        return ServerResponse.responseWithSuccessMessage("操作成功");
    }
}
