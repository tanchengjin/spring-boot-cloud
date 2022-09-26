package com.tanchengjin.oauth2.conf.security.oauth2.provider;

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
public class MobilePasswordTokenGranter extends AbstractTokenGranter {
    private final AuthenticationManager authenticationManager;

    public MobilePasswordTokenGranter(AuthenticationManager manager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, "mobile_password");
        this.authenticationManager = manager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> requestParameters = tokenRequest.getRequestParameters();
        String mobile = requestParameters.get("mobile");
        String password = requestParameters.get("password");
//        requestParameters.keySet("password");
        MobilePasswordAuthenticationToken authenticationToken = new MobilePasswordAuthenticationToken(mobile, password);
        authenticationToken.setDetails(requestParameters);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (authenticate == null || !authenticate.isAuthenticated()) {
            throw new InvalidGrantException("cloud not authentication mobile " + mobile);
        }
        OAuth2Request oAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(oAuth2Request,authenticate);
    }
}
