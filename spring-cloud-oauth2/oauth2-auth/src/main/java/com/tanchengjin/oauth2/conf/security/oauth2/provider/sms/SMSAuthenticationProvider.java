package com.tanchengjin.oauth2.conf.security.oauth2.provider.sms;

import com.tanchengjin.oauth2.modules.user.pojo.User;
import com.tanchengjin.oauth2.modules.user.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@Component
public class SMSAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;

    public SMSAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SMSAuthenticationToken authenticationToken = (SMSAuthenticationToken) authentication;
        String mobile = (String) authenticationToken.getPrincipal();
        String code = (String) authenticationToken.getCredentials();
        User userByMobile = userService.getUserByMobile(mobile);
        if (userByMobile == null) {
            throw new AuthenticationServiceException("sms authentication failed");
        }
//        DefaultUserDetails userDetails = UserUtil.userConvertToUserDetail(userByMobile);
        SMSAuthenticationToken smsAuthenticationToken = new SMSAuthenticationToken(mobile, code,new ArrayList<GrantedAuthority>());
        smsAuthenticationToken.setDetails("");
        return smsAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return SMSAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
