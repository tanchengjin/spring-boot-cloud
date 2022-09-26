package com.tanchengjin.oauth2.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@RestController
public class IndexController {
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String index() {
        return "test method";
    }
}
