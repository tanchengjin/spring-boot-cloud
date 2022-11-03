package com.tanchengjin.oauth2.test.controller;

import com.tanchengjin.auth.AuthUtil;
import com.tanchengjin.auth.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


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

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Object getUserInfo(HttpServletRequest request) {
        User user = AuthUtil.getUser(request);
        return user;
    }
}
