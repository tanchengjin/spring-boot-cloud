//package com.tanchengjin.oauth2.conf.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.provider.*;
//import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
//import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
//import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
//import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
//import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
//import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
//import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
//import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
//import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
//import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @Author TanChengjin
// * @Email 18865477815@163.com
// * @Since V1.0.0
// **/
//@Configuration
//public class TokenGranterConfig {
//    private final ClientDetailsService clientDetailsService;
//
//    private final AuthenticationManager authenticationManager;
//
//    private final RandomValueAuthorizationCodeServices authorizationCodeServices;
//
//    private TokenGranter tokenGranter;
//
//
//    public TokenGranterConfig(@Autowired(required = false) ClientDetailsService clientDetailsService, @Autowired(required = false) AuthenticationManager authenticationManager, @Autowired(required = false) RandomValueAuthorizationCodeServices authorizationCodeServices) {
//        this.clientDetailsService = clientDetailsService;
//        this.authenticationManager = authenticationManager;
//        if (authorizationCodeServices == null) {
//            this.authorizationCodeServices = new InMemoryAuthorizationCodeServices();
//        } else {
//            this.authorizationCodeServices = authorizationCodeServices;
//        }
//    }
//
//    @Bean
//    public TokenGranter tokenGranter(DefaultTokenServices tokenServices) {
//        if (tokenGranter == null) {
//            tokenGranter = new TokenGranter() {
//                private CompositeTokenGranter compositeTokenGranter;
//                @Override
//                public OAuth2AccessToken grant(String s, TokenRequest tokenRequest) {
//                    compositeTokenGranter = new CompositeTokenGranter(getTokenGranterList(tokenServices));
//                    return compositeTokenGranter.grant(s, tokenRequest);
//                }
//            };
//        }
//        return tokenGranter;
//    }
//
//    private List<TokenGranter> getTokenGranterList(DefaultTokenServices tokenServices) {
//        DefaultOAuth2RequestFactory requestFactory = new DefaultOAuth2RequestFactory(clientDetailsService);
//        //获得当前默认的一些授权模式
//        List<TokenGranter> defaultTokenGranters = getDefaultTokenGranters(tokenServices, authorizationCodeServices, requestFactory);
//        if (authenticationManager != null) {
//            //手机号+密码登录
//            defaultTokenGranters.add(new MobilePasswordTokenGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory));
//        }
//        return defaultTokenGranters;
//    }
//
//    /**
//     * 默认的授权模式
//     */
//    private List<TokenGranter> getDefaultTokenGranters(AuthorizationServerTokenServices tokenServices
//            , AuthorizationCodeServices authorizationCodeServices, OAuth2RequestFactory requestFactory) {
//        List<TokenGranter> tokenGranters = new ArrayList<>();
//        // 添加授权码模式
//        tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetailsService, requestFactory));
//        // 添加刷新令牌的模式
//        tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetailsService, requestFactory));
//        // 添加隐士授权模式
//        tokenGranters.add(new ImplicitTokenGranter(tokenServices, clientDetailsService, requestFactory));
//        // 添加客户端模式
//        tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetailsService, requestFactory));
//        if (authenticationManager != null) {
//            // 添加密码模式
//            tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory));
//        }
//        return tokenGranters;
//    }
//
//
//
//
//
//}
