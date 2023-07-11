package com.tanchengjin.oauth2.conf.security.oauth2;

import com.tanchengjin.oauth2.conf.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        HashMap<String, Object> additionalInformation = new HashMap<>();
        Object principal = oAuth2Authentication.getPrincipal();
        if(principal instanceof User)
        {
            User user = (User) principal;
            additionalInformation.put("user_id", user.getId());
            additionalInformation.put("user_name", user.getUsername());
        }
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInformation);
        return oAuth2AccessToken;
    }
}
