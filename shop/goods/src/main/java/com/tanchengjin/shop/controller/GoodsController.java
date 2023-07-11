package com.tanchengjin.shop.controller;

import com.tanchengjin.util.ServerResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    private final static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ServerResponse list() {
        throw new RuntimeException("测试");
//        return ServerResponse.responseWithSuccessMessage("操作成功");
    }
}
