package com.tanchengjin.oauth2.controller;

import com.tanchengjin.oauth2.modules.oauth.enumeration.QRCodeStateEnum;
import com.tanchengjin.oauth2.modules.oauth.request.MiniProgramLoginRequest;
import com.tanchengjin.oauth2.modules.user.mapper.UserMapper;
import com.tanchengjin.oauth2.modules.user.request.UserRequest;
import com.tanchengjin.oauth2.modules.user.service.UserService;
import com.tanchengjin.oauth2.util.OAuth2Util;
import com.tanchengjin.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

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

    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private TokenEnhancer jwtTokenEnhancer;//自定义jwt生成数据模板
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    private DefaultTokenServices defaultTokenServices;

    @Autowired
    private OAuth2Util oAuth2Util;

    private final RedisTemplate<String, Object> redisTemplate;

    public AuthController(UserService userService, UserMapper userMapper, RedisTemplate<String, Object> redisTemplate) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.redisTemplate = redisTemplate;
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

    /**
     * 扫码登录生成uuid
     *
     * @return
     */
    @PostMapping(value = "/qrcode/uuid")
    public ServerResponse<String> qrcodeUUID() {
        String uuid = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(uuid + "_state", QRCodeStateEnum.PENDING.getCode());
        return ServerResponse.responseWithSuccess(uuid);
    }

    /**
     * 扫码登录二维码扫描
     *
     * @return
     */
    @PostMapping(value = "/qrcode/confirm/{uuid}")
    public ServerResponse<String> qrcodeConfirm(@PathVariable(value = "uuid") String uuid) {
        redisTemplate.opsForValue().set(uuid + "_state", QRCodeStateEnum.CONFIRM.getCode());
        return ServerResponse.responseWithSuccess();
    }

    /**
     * 扫码登录二维码确认(授权)
     *
     * @return
     */
    @PostMapping(value = "/qrcode/authorization/{uuid}")
    public ServerResponse<String> qrcodeLogin(@PathVariable(value = "uuid") String uuid, HttpServletRequest request, Authentication authentication) {
        redisTemplate.opsForValue().set(uuid + "_state", QRCodeStateEnum.AUTHORIZATION.getCode());
        return ServerResponse.responseWithSuccess("授权成功!");
    }

    /**
     * 扫码登录二维码确认(获取token)
     *
     * @return
     */
    @PostMapping(value = "/qrcode/token/{uuid}")
    public ServerResponse<String> qrcodeToken(@PathVariable(value = "uuid") String uuid, HttpServletRequest request, Authentication authentication) {
        Object o = redisTemplate.opsForValue().get(uuid + "_state");
        if (o instanceof Integer) {
            if (((int) o) == QRCodeStateEnum.AUTHORIZATION.getCode()) {
                redisTemplate.opsForValue().set(uuid + "_state", QRCodeStateEnum.FINISH.getCode());
                OAuth2AccessToken oauth2AccessToken = oAuth2Util.getOauth2AccessToken("f51ce644-5d92-4fe6-bee9-aa88b1708f82", authentication);
                return ServerResponse.responseWithSuccess(oauth2AccessToken.getValue());
            }
            return ServerResponse.responseWithFailure("非法请求!");
        }
        return ServerResponse.responseWithFailure("服务器错误!");
    }
}
