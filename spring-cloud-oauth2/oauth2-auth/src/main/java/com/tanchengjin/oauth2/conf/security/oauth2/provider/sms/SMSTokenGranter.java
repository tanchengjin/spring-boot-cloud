package com.tanchengjin.oauth2.conf.security.oauth2.provider.sms;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
public class SMSTokenGranter extends AbstractTokenGranter {
    private final AuthenticationManager authenticationManager;

    public SMSTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, AuthenticationManager authenticationManager) {
        super(tokenServices, clientDetailsService, requestFactory, "sms");
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> requestParameters = tokenRequest.getRequestParameters();
        String mobile = requestParameters.getOrDefault("mobile", "");
        String code = requestParameters.getOrDefault("code", "");
        if ("".equals(mobile) || "".equals(code)) {
            throw new InvalidGrantException("cloud not authentication sms");
        }
        SMSAuthenticationToken smsAuthenticationToken = new SMSAuthenticationToken(mobile, code);
        smsAuthenticationToken.setDetails(requestParameters);
        Authentication authenticate = authenticationManager.authenticate(smsAuthenticationToken);
        OAuth2Request oAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(oAuth2Request, authenticate);
    }
}
