//package com.tanchengjin.oauth2.conf;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenEnhancer;
//import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
//
//import java.security.KeyPair;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @Author TanChengjin
// * @Email 18865477815@163.com
// * @Since V1.0.0
// **/
//@Configuration
//@EnableAuthorizationServer
//public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {
//    private final PasswordEncoder passwordEncoder;
//    private final JwtTokenEnhancer jwtTokenEnhancer;
//    private final AuthenticationManager authenticationManager;
//    private final UserDetailsService userDetailsService;
//
//    public Oauth2ServerConfig(PasswordEncoder passwordEncoder, JwtTokenEnhancer jwtTokenEnhancer, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
//        this.passwordEncoder = passwordEncoder;
//        this.jwtTokenEnhancer = jwtTokenEnhancer;
//        this.authenticationManager = authenticationManager;
//        this.userDetailsService = userDetailsService;
//    }
//
//
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        /**
//         * 主要是让/oauth/token支持client_id和client_secret做登陆认证如果开启了allowFormAuthenticationForClients，那么就在BasicAuthenticationFilter之前
//         * 添加ClientCredentialsTokenEndpointFilter,使用ClientDetailsUserDetailsService来进行登陆认证
//         */
//        security.allowFormAuthenticationForClients();
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("client-app")
//                .secret(passwordEncoder.encode("123456"))
//                .scopes("all")
//                .authorizedGrantTypes("password", "refresh_token")
//                .accessTokenValiditySeconds(3600)
//                .refreshTokenValiditySeconds(86400);
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
//        List<TokenEnhancer> tokenEnhancerChains = new ArrayList<>();
//        tokenEnhancerChains.add(jwtTokenEnhancer);
//        tokenEnhancerChains.add(jwtAccessTokenConverter());
//        tokenEnhancerChain.setTokenEnhancers(tokenEnhancerChains);
//
//        endpoints
//                .authenticationManager(authenticationManager)
//                .userDetailsService(userDetailsService)
//                .accessTokenConverter(jwtAccessTokenConverter())
//                .tokenEnhancer(tokenEnhancerChain);
//
//        super.configure(endpoints);
//    }
//
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter() {
//        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
////        jwtAccessTokenConverter.setKeyPair(keyPair());
//        return jwtAccessTokenConverter;
//    }
//
////    public KeyPair keyPair() {
////        //从classpath下的证书中获取秘钥对
////        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "123456".toCharArray());
////        return keyStoreKeyFactory.getKeyPair("jwt", "123456".toCharArray());
////    }
//
//
//}
