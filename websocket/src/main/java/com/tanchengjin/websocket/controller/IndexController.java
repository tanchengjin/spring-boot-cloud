package com.tanchengjin.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@Controller
public class IndexController {
    @GetMapping(value = {"", "/"})
    @ResponseBody
    public String index() {
        return "app controller";
    }
}
