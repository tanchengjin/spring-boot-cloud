package com.tanchengjin.oauth2.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

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

    @Autowired
    private KeyPair keyPair;

    @GetMapping("/getPublicKey")
    public Map<String, Object> getPublicKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }
}
