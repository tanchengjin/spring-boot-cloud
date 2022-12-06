package com.tanchengjin.oauth2.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@Component
public class OAuth2Util {
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


    public OAuth2AccessToken getOauth2AccessToken(String clientId,Authentication authentication) {

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        TokenRequest tokenRequest = null;
        //4. 通过 TokenRequest构造器生成 TokenRequest
        tokenRequest = new TokenRequest(new HashMap<>(), clientId, clientDetails.getScope(), "custom");

        // 通过 TokenRequest的 createOAuth2Request方法获取 OAuth2Request
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        // 通过 Authentication和 OAuth2Request构造出 OAuth2Authentication
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        //令牌转由普通令牌转换为JWT
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancers = new ArrayList<>();
        enhancers.add(jwtTokenEnhancer);//自定义JWT模板
        enhancers.add(jwtAccessTokenConverter);
        tokenEnhancerChain.setTokenEnhancers(enhancers);
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);
        OAuth2AccessToken accessToken = defaultTokenServices.createAccessToken(oAuth2Authentication);

        // 8. 返回 Access Token
        return accessToken;
    }

//    public ServerResponse<String> qrcodeLogin(@PathVariable(value = "uuid") String uuid, HttpServletRequest request, Authentication authentication) {
//        OAuth2AccessToken oauth2Authentication = oAuth2Util.getOauth2Authentication("f51ce644-5d92-4fe6-bee9-aa88b1708f82");
//        // 1. 从请求头中获取 ClientId
////        String header = request.getHeader("Authorization");
////        if (header == null || !header.startsWith("Basic ")) {
////            throw new UnapprovedClientAuthenticationException("请求头中无client信息");
////        }
////
////        String[] tokens = this.extractAndDecodeHeader(header, request);
////        String clientId = tokens[0];
////        String clientSecret = tokens[1];
////
////
////        // 2. 通过 ClientDetailsService 获取 ClientDetails
////        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
//
//        // 3. 校验 ClientId和 ClientSecret的正确性
////        if (clientDetails == null) {
////            throw new UnapprovedClientAuthenticationException("clientId:" + clientId + "对应的信息不存在");
////        } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
////            throw new UnapprovedClientAuthenticationException("clientSecret不正确");
////        } else {
////            // 4. 通过 TokenRequest构造器生成 TokenRequest
////            tokenRequest = new TokenRequest(new HashMap<>(), clientId, clientDetails.getScope(), "custom");
////        }
//
//        ClientDetails clientDetails = clientDetailsService.loadClientByClientId("f51ce644-5d92-4fe6-bee9-aa88b1708f82");
//
//        TokenRequest tokenRequest = null;
//        tokenRequest = new TokenRequest(new HashMap<>(), "f51ce644-5d92-4fe6-bee9-aa88b1708f82", clientDetails.getScope(), "custom");
//
//        // 5. 通过 TokenRequest的 createOAuth2Request方法获取 OAuth2Request
//        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
//        // 6. 通过 Authentication和 OAuth2Request构造出 OAuth2Authentication
//        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
//
//        // 7. 通过 AuthorizationServerTokenServices 生成 OAuth2AccessToken
////        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
////        defaultTokenServices.setTokenStore(tokenStore);
//        //令牌转由普通令牌转换为JWT
//        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
//        List<TokenEnhancer> enhancers = new ArrayList<>();
//        enhancers.add(jwtTokenEnhancer);//自定义JWT模板
//        enhancers.add(jwtAccessTokenConverter);
//        tokenEnhancerChain.setTokenEnhancers(enhancers);
//        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);
//        OAuth2AccessToken accessToken = defaultTokenServices.createAccessToken(oAuth2Authentication);
////        OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices.refreshAccessToken("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NjkzNDYxNTMsInVzZXJJZCI6IjEyMyIsInVzZXJfbmFtZSI6InJvb3QiLCJqdGkiOiJjZDE3OTBhNi1mZGMyLTRkODQtODM4My0yNTM5YWU5NjNiYjMiLCJjbGllbnRfaWQiOiJmNTFjZTY0NC01ZDkyLTRmZTYtYmVlOS1hYTg4YjE3MDhmODIiLCJzY29wZSI6WyJhbGwiXX0.Nka7FfQ3D66zmQ83SOoiPL5HRWqy7uLnv93TdoHZKgdk9d2PV_3RaGzjAxePblhBahFUYLRNMh7XWoH3odqiYCdXTF13ZCTf1Pf14a10EdCfcHjTFjIUvchsz-WI1xvn6Mu6G06dGP4epx8yZVvnWo5yp6t8pbND3HczxxIvy0w",tokenRequest);
//
//        // 8. 返回 Token
//
//        return ServerResponse.responseWithSuccess(accessToken.getValue());
//    }
}
