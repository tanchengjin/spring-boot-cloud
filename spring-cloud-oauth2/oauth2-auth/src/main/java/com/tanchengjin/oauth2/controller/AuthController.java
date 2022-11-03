package com.tanchengjin.oauth2.controller;

import com.tanchengjin.oauth2.modules.oauth.enumeration.OauthTypeEnum;
import com.tanchengjin.oauth2.modules.oauth.request.MiniProgramLoginRequest;
import com.tanchengjin.oauth2.modules.user.mapper.UserMapper;
import com.tanchengjin.oauth2.modules.user.request.UserRequest;
import com.tanchengjin.oauth2.modules.user.service.UserService;
import com.tanchengjin.util.ServerResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@RequestMapping("/auth")
@RestController
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ServerResponse register(@RequestBody @Validated(value = UserRequest.register.class) UserRequest userRequest) {
        if (userMapper.existsUserByUsername(userRequest.getUsername())) {
            return ServerResponse.responseWithFailure("用户已存在!");
        }
        if (userService.registerUser(userRequest)) {
            return ServerResponse.responseWithSuccess("注册成功!");
        } else {
            return ServerResponse.responseWithFailure("注册失败，请重试!");
        }
    }

    @Deprecated
    @RequestMapping(value = "/login_by_miniprogram", method = RequestMethod.POST)
    public ServerResponse loginByMiniProgram(@RequestBody MiniProgramLoginRequest request) {
        userService.createUserByMiniProgram(request);
        return ServerResponse.responseWithSuccess("注册成功!");
    }
}
