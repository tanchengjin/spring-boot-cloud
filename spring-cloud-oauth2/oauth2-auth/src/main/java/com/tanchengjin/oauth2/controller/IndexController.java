package com.tanchengjin.oauth2.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@RestController
public class IndexController {
    @GetMapping(value = {"/index"})
    public String index() {
        return "index";
    }

    @GetMapping("/userinfo")
    public UserDetails userinfo(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return principal;
    }

    @GetMapping("/authenticationInfo")
    public Authentication authenticationInfo(Authentication authentication) {
        return authentication;
    }
}
