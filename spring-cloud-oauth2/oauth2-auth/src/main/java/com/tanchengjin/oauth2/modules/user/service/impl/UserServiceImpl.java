package com.tanchengjin.oauth2.modules.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tanchengjin.oauth2.conf.security.oauth2.OauthConfig;
import com.tanchengjin.oauth2.modules.oauth.enumeration.OauthTypeEnum;
import com.tanchengjin.oauth2.modules.oauth.mapper.OauthMapper;
import com.tanchengjin.oauth2.modules.oauth.pojo.Oauth;
import com.tanchengjin.oauth2.modules.oauth.request.MiniProgramLoginRequest;
import com.tanchengjin.oauth2.modules.oauth.request.OauthLoginRequest;
import com.tanchengjin.oauth2.modules.oauth.service.OauthService;
import com.tanchengjin.oauth2.modules.user.enumeration.SocialEnum;
import com.tanchengjin.oauth2.modules.user.mapper.UserMapper;
import com.tanchengjin.oauth2.modules.user.pojo.User;
import com.tanchengjin.oauth2.modules.user.request.UserRequest;
import com.tanchengjin.oauth2.modules.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * create by  user ServiceImpl
 *
 * @author Tanchengjin
 * @version v1.0.0
 * @since v1.0.0
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final RestTemplate restTemplate;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final OauthService oauthService;
    private final OauthMapper oauthMapper;
    private final OauthConfig oauthConfig;


    public UserServiceImpl(RestTemplate restTemplate, UserMapper userMapper, PasswordEncoder passwordEncoder, OauthService oauthService, OauthMapper oauthMapper, OauthConfig oauthConfig) {
        this.restTemplate = restTemplate;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.oauthService = oauthService;
        this.oauthMapper = oauthMapper;
        this.oauthConfig = oauthConfig;
    }

    @Override
    @Deprecated
    public boolean registerUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setNickname(userRequest.getUsername());
        return this.save(user);
    }

    @Override
    public boolean createUserByUsernameAndPassword(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setNickname(userRequest.getUsername());
        return this.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createUserByMiniProgram(OauthLoginRequest loginRequest) {
        MiniProgramLoginRequest request = (MiniProgramLoginRequest) loginRequest;
//        String openId = getOpenIdByCode("wx6ad144e54af67d87", "91a2ff6d38a2bbccfb7e9f9079108e2e", request.getCode());
        String openId = getOpenIdByCode(oauthConfig.getMiniprogram().getApp_id(), oauthConfig.getMiniprogram().getApp_secret(), request.getCode());
        if (openId != null && !"".equals(openId) && openId.length() >= 1) {
            User user = new User();
            user.setUsername(generateStr(16));
            user.setNickname(request.getUserInfo().getNickName());
            user.setAvatar(request.getUserInfo().getAvatarUrl());
            user.setSex(request.getUserInfo().getGender());
            user.setPassword(passwordEncoder.encode(generateStr(32)));
            boolean userByOauth = createUserByOauth(openId, user, OauthTypeEnum.MINIPROGRAM);
            if (userByOauth) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getUserByMobile(String mobile) {
        return this.baseMapper.selectUserByMobile(mobile);
    }

    @Override
    public User getUserByUsername(String username) {
        return this.baseMapper.selectUserByUsername(username);
    }

    //    @Transactional(rollbackFor = Exception.class)
    boolean createUserByOauth(String token, User user, OauthTypeEnum oauthTypeEnum) {
        if (!oauthMapper.existsByToken(token)) {
            this.save(user);
            Oauth oauth = new Oauth();
            oauth.setOauthType(oauthTypeEnum.getType());
            oauth.setToken(token);
            oauth.setUserId(user.getId());
            oauthService.save(oauth);
            return true;
        }
        return true;
    }


    /**
     * 生成随机字符串
     *
     * @param length 长度
     * @return
     */
    private String generateStr(int length) {
        String rdm = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int i1 = random.nextInt(rdm.length() - 1);
            char c = rdm.charAt(i1);
            stringBuffer.append(c);
        }
        return stringBuffer.toString();
    }

    /**
     * 通过用户凭证获取微信openId unionid session_key等信息
     *
     * @param appid  小程序 appid
     * @param secret 小程序secret
     * @param code   小程序code wx.login()
     * @return 小程序raw原格式
     */
    public String getOpenIdByCode(String appid, String secret, String code) {
        String http = String.format(SocialEnum.WX_AUTH_JSCODE.getUrl(), appid, secret, code);
        String forObject = restTemplate.getForObject(http, String.class);
        logger.info("get open id [{}]", forObject);
        JSONObject jsonObject = JSONObject.parseObject(forObject);
        return (String) jsonObject.get("openid");
    }


    //    public String getOpenIdByCode(String code) {
//        String http = String.format(SocialEnum.WX_AUTH_JSCODE.getUrl(), appid, secret, code);
//        System.out.println(http);
//        String forObject = restTemplate.getForObject(http, String.class);
//        JSONObject jsonObject = null;
//        jsonObject = JSONObject.fromObject(forObject);
//        Iterator keys = jsonObject.keys();
//        HashMap<String, Object> o = new HashMap<>();
//        while (keys.hasNext()) {
//            String key = (String) keys.next();
//            Object value = jsonObject.get(key);
//            o.put(key, value);
//        }
//        return o.get("openid").toString();
//    }
}