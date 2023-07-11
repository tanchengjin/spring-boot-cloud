package com.tanchengjin.oauth2.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import com.tanchengjin.auth.AuthUtil;
import com.tanchengjin.auth.Userinfo;
import com.tanchengjin.oauth2.modules.oauth.enumeration.OauthTypeEnum;
import com.tanchengjin.oauth2.modules.oauth.enumeration.QRCodeStateEnum;
import com.tanchengjin.oauth2.modules.oauth.pojo.Oauth;
import com.tanchengjin.oauth2.modules.oauth.request.MiniProgramLoginRequest;
import com.tanchengjin.oauth2.modules.oauth.service.OauthService;
import com.tanchengjin.oauth2.modules.oauth.vo.OAuthVO;
import com.tanchengjin.oauth2.modules.user.mapper.UserMapper;
import com.tanchengjin.oauth2.modules.user.request.UserRequest;
import com.tanchengjin.oauth2.modules.user.service.UserService;
import com.tanchengjin.oauth2.util.OAuth2Util;
import com.tanchengjin.util.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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
    private final OauthService oauthService;

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

    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(UserService userService, UserMapper userMapper, OauthService oauthService, RedisTemplate<String, Object> redisTemplate) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.oauthService = oauthService;
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

    @RequestMapping(value = "/oauthList", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse<List<OAuthVO>> oauthList(HttpServletRequest request) {
        List<OAuthVO> oauthList = oauthService.getOauthBindedListByUserId(AuthUtil.getUser(request).getId());
        return ServerResponse.responseWithSuccess(oauthList);
    }

    @RequestMapping(value = "/gitee", method = {RequestMethod.GET})
    public String gitee() {
        final String client = "9d672d6e74b20a36618487c0327d19a63329151b41e90696a890f4687b98f0aa";
        final String secret = "b3c83df2e1a996f6fcce42e4238c480caed2adef910bcb5f4b4a4576e73dfe5b";
        String url = String.format("https://gitee.com/oauth/authorize?client_id=%s&redirect_uri={redirect_uri}&res+ponse_type=code", client);
        HttpRequest httpRequest = HttpRequest.get(url);
        return null;
    }

    @RequestMapping(value = "/gitee/callback", method = {RequestMethod.GET})
    public ServerResponse giteeCallback(@RequestParam("code") String code, HttpServletResponse response,HttpServletRequest request,@RequestParam("uid") String uid) throws IOException {
        final String client = "9d672d6e74b20a36618487c0327d19a63329151b41e90696a890f4687b98f0aa";
        final String secret = "b3c83df2e1a996f6fcce42e4238c480caed2adef910bcb5f4b4a4576e73dfe5b";
        final String redirectUrl = "http://localhost:8002/oauth2-auth/auth/gitee/callback";
        final String url = String.format("https://gitee.com/oauth/token?grant_type=authorization_code&code=%s&client_id=%s&redirect_uri=%s&client_secret=%s", code, client, redirectUrl, secret);
        HttpResponse execute = HttpRequest.post(url).execute();
        String string = execute.body();
        Map parse = (Map) JSONObject.parse(string);
        if (execute.getStatus() != 200) {
            logger.error(parse.toString());
            final String errMsg = parse.containsKey("error_description") ? parse.get("error_description").toString() : "Token获取失败!";
            return ServerResponse.responseWithFailureMessage(errMsg);
        }


        Oauth oauth = new Oauth();
        oauth.setOauthType(OauthTypeEnum.GITEE.getType());
        oauth.setToken(parse.get("access_token").toString());
        oauthService.save(oauth);
        response.sendRedirect("http://localhost:8080/#/home");
        return null;
    }
}
