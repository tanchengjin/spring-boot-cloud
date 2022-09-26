package com.tanchengjin.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@RestController
@RequestMapping("/oauth2")
public class SecurityOAuth2Controller {
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public void index()
    {

    }
}
