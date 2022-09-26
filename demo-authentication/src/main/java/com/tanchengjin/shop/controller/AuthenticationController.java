package com.tanchengjin.shop.controller;

import com.tanchengjin.common.util.JwtUtil;
import com.tanchengjin.shop.util.ServerResponse;
import com.tanchengjin.shop.util.vo.JwtTokenVO;
import com.tanchengjin.shop.vo.LoginOrRegisterVO;
import com.tanchengjin.util.JWTUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Random;

/**
 * @author TanChengjin
 * @version v1.0.0
 * @email 18865477815@163.com
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ServerResponse login(@RequestBody LoginOrRegisterVO loginOrRegisterVO) {
        int i = new Random().nextInt(5);
        if (i >= 3) {
            return ServerResponse.responseWithFailureMessage("登录失败!");
        }

        JwtTokenVO jwtTokenVO = new JwtTokenVO();
        jwtTokenVO.setToken(JWTUtil.generateToken(1, "user1"));
//        jwtTokenVO.setToken(JwtUtil.generateToken());
        return ServerResponse.responseWithSuccessMessage("登录成功", jwtTokenVO);
    }

    @RequestMapping("/register")
    public ServerResponse register() {
        int i = new Random().nextInt(5);
        if (i >= 3) {
            return ServerResponse.responseWithFailureMessage("注册失败!");
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("token", JwtUtil.generateToken());
        return ServerResponse.responseWithSuccess(data, "注册成功");
    }

    @RequestMapping("/test")
    public ServerResponse test(@RequestParam("token") String token) {
        boolean b = JwtUtil.checkToken(token);
        if (b) {
            return ServerResponse.responseWithSuccessMessage("认证成功!");
        }
        return ServerResponse.responseWithFailureMessage("认证失败!");
    }

}
