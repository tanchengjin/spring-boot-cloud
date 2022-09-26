package com.tanchengjin.oauth2.conf.security.oauth2.provider.miniprogram;

import com.alibaba.fastjson.JSONObject;
import com.tanchengjin.oauth2.modules.oauth.request.MiniProgramLoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
public class MiniProgramTokenGranter extends AbstractTokenGranter {
    private final static Logger logger = LoggerFactory.getLogger(MiniProgramTokenGranter.class);
    private final AuthenticationManager authenticationManager;

    public MiniProgramTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, AuthenticationManager authenticationManager) {
        super(tokenServices, clientDetailsService, requestFactory, "miniprogram");
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        String errorMsg = "cloud not authentication miniprogram";
        MiniProgramLoginRequest postBody = getPostBody();

        if (postBody == null) {
            logger.error("{} missing parameter", errorMsg);
            throw new InvalidGrantException(errorMsg);
        }
        logger.info("miniprogram parameter [{}]", postBody);

        MiniProgramAuthenticationToken miniProgramAuthenticationToken = new MiniProgramAuthenticationToken(postBody);
        Authentication authenticate = authenticationManager.authenticate(miniProgramAuthenticationToken);
        if (authenticate == null || !authenticate.isAuthenticated()) {
            throw new InvalidGrantException(errorMsg);
        }
        OAuth2Request oAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(oAuth2Request, authenticate);
    }

    private MiniProgramLoginRequest getPostBody() {
        MiniProgramLoginRequest miniProgramLoginRequest = null;
        HttpServletRequest request =
                ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        try {
            BufferedReader reader = request.getReader();
            StringBuilder result = new StringBuilder();
            String str = "";
            while ((str = reader.readLine()) != null) {
                result.append(str);
            }

            logger.info(result.toString());
            miniProgramLoginRequest = JSONObject.parseObject(String.valueOf(result), MiniProgramLoginRequest.class);

        } catch (IOException e) {
            throw new InvalidGrantException("认证失败，请重试!");
        }
        return miniProgramLoginRequest;
    }
}
