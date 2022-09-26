package com.tanchengjin.oauth2.conf.security.oauth2.provider;

import com.tanchengjin.oauth2.modules.user.pojo.User;
import com.tanchengjin.oauth2.modules.user.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@Component
public class MobilePasswordAuthenticationProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public MobilePasswordAuthenticationProvider(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobilePasswordAuthenticationToken authenticationToken = (MobilePasswordAuthenticationToken) authentication;
        String mobile = (String) authenticationToken.getPrincipal();
        String password = (String) authenticationToken.getCredentials();
        User userByMobile = userService.getUserByMobile(mobile);
        if (userByMobile == null) {
            throw new AuthenticationServiceException("手机号或密码错误");
        }
        if (!passwordEncoder.matches(password, userByMobile.getPassword())) {
            throw new AuthenticationServiceException("手机号或密码错误");
        }
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(userByMobile.getUsername(), userByMobile.getPassword(), grantedAuthorities);
        MobilePasswordAuthenticationToken authenticationTokenResult = new MobilePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        authenticationTokenResult.setDetails(authenticationToken.getDetails());
        return authenticationTokenResult;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (MobilePasswordAuthenticationToken.class.isAssignableFrom(aClass));
    }
}