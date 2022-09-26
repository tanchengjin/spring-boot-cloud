package com.tanchengjin.oauth2.conf.security.oauth2.provider.miniprogram;

import com.alibaba.fastjson.JSONObject;
import com.tanchengjin.oauth2.modules.oauth.request.MiniProgramLoginRequest;
import com.tanchengjin.oauth2.modules.user.pojo.User;
import com.tanchengjin.oauth2.modules.user.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@Component
public class MiniProgramAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;

    public MiniProgramAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MiniProgramAuthenticationToken token = (MiniProgramAuthenticationToken) authentication;
        MiniProgramLoginRequest miniProgramLoginRequest = token.getRequest();
        User userByMiniProgram = userService.createUserByMiniProgram(miniProgramLoginRequest);
        MiniProgramAuthenticationToken miniProgramAuthenticationToken = new MiniProgramAuthenticationToken(new ArrayList<>(), userByMiniProgram.getUsername(), userByMiniProgram.getPassword());
        miniProgramAuthenticationToken.setDetails(token.getDetails());
        return miniProgramAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return MiniProgramAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
