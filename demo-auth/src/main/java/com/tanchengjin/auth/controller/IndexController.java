package com.tanchengjin.auth.controller;

import com.tanchengjin.util.ServerResponse;
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
    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public ServerResponse index() {
        return ServerResponse.responseWithSuccess();
    }
}
