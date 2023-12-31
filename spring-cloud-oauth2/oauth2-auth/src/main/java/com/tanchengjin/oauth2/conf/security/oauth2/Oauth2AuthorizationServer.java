package com.tanchengjin.oauth2.conf.security.oauth2;

import com.tanchengjin.oauth2.conf.security.oauth2.provider.MobilePasswordTokenGranter;
import com.tanchengjin.oauth2.conf.security.oauth2.provider.miniprogram.MiniProgramTokenGranter;
import com.tanchengjin.oauth2.conf.security.oauth2.provider.sms.SMSTokenGranter;
import com.tanchengjin.oauth2.modules.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EnableAuthorizationServer
@Order(1)
@Configuration
public class Oauth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;


    public Oauth2AuthorizationServer(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }


    /**
     * 配置：安全检查流程,用来配置令牌端点（Token Endpoint）的安全与权限访问
     * 默认过滤器：BasicAuthenticationFilter
     * 1、oauth_client_details表中clientSecret字段加密【ClientDetails属性secret】
     * 2、CheckEndpoint类的接口 oauth/check_token 无需经过过滤器过滤，默认值：denyAll()
     * 对以下的几个端点进行权限配置：
     * /oauth/authorize：授权端点
     * /oauth/token：令牌端点
     * /oauth/confirm_access：用户确认授权提交端点
     * /oauth/error：授权服务错误信息端点
     * /oauth/check_token：用于资源服务访问的令牌解析端点
     * /oauth/token_key：提供公有密匙的端点，如果使用JWT令牌的话
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .allowFormAuthenticationForClients() // 允许client_id/client_secret 在参数中校验
                .passwordEncoder(passwordEncoder)
                .checkTokenAccess("isAuthenticated()") // 开启/oauth/check_token验证端口认证权限访问
                .tokenKeyAccess("permitAll()"); // 开启/oauth/token_key验证端口无权限访问

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //增强jwt token
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));

        endpoints
                .authenticationManager(authenticationManager)
                .tokenGranter(tokenGranter(endpoints))
                .accessTokenConverter(jwtAccessTokenConverter())
                .userDetailsService(userDetailsService)
                .tokenEnhancer(tokenEnhancerChain)
                .tokenStore(tokenStore());

        //将用户信息放入到principal
        DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
        DefaultUserAuthenticationConverter defaultUserAuthenticationConverter = new DefaultUserAuthenticationConverter();
        defaultUserAuthenticationConverter.setUserDetailsService(userDetailsService);
        defaultAccessTokenConverter.setUserTokenConverter(defaultUserAuthenticationConverter);
        endpoints.accessTokenConverter(defaultAccessTokenConverter);
    }

    @Bean
    public UserAuthenticationConverter userAuthenticationConverter() {
        DefaultUserAuthenticationConverter defaultUserAuthenticationConverter = new DefaultUserAuthenticationConverter();
        defaultUserAuthenticationConverter.setUserDetailsService(userDetailsService);
        return defaultUserAuthenticationConverter;
    }


    /**
     * 重点
     * 先获取已经有的五种授权，然后添加我们自己的进去
     *
     * @param endpoints AuthorizationServerEndpointsConfigurer
     * @return TokenGranter
     */
    private TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> granters = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));
//        granters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
//
//        granters.add(new RefreshTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
//
//        granters.add(new AuthorizationCodeTokenGranter(endpoints.getTokenServices(), endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
//
//        granters.add(new ImplicitTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));

        //手机号+密码
        granters.add(new MobilePasswordTokenGranter(authenticationManager, endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
        //手机号+验证码
        granters.add(new SMSTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), authenticationManager));
        //miniprogram
        granters.add(new MiniProgramTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), authenticationManager));
        return new CompositeTokenGranter(granters);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("f51ce644-5d92-4fe6-bee9-aa88b1708f82")
                .secret(passwordEncoder.encode("2211e824-efcb-43c2-ab8d-576bd364ddd3"))
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(86400)
                .authorizedGrantTypes("password", "refresh_token", "mobile_password", "sms", "miniprogram")
                .scopes("admin", "all", "global");

    }

    @Bean
    @Primary
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }


    /**
     * 将默认的token转换成Jwt Token
     *
     * @return JwtAccessTokenConverter.class
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
//        jwtAccessTokenConverter.extractAccessToken();
//        JwtTokenEnhancer jwtTokenEnhancer = new JwtTokenEnhancer();
        jwtAccessTokenConverter.setKeyPair(keyPair());
//        jwtAccessTokenConverter.setSigningKey("c1R73cGB4tm2Ghx7vEc3iOqVJjrT86zL");
//        jwtAccessTokenConverter.setAccessTokenConverter();
        final AccessTokenConverter accessTokenConverter = jwtAccessTokenConverter.getAccessTokenConverter();
        if (accessTokenConverter instanceof DefaultAccessTokenConverter) {
            ((DefaultAccessTokenConverter) accessTokenConverter).setUserTokenConverter(userAuthenticationConverter());
        }
        return jwtAccessTokenConverter;
    }

    /**
     * 从classpath下的密钥库中获取密钥对(公钥+私钥)
     */
    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(
                new ClassPathResource("jwt.jks"), "baojian".toCharArray());
        KeyPair keyPair = factory.getKeyPair(
                "baojian", "baojian".toCharArray());
        return keyPair;
    }

    /**
     * 令牌的存储类型
     * Spring Security在用户登录成功后会将token存储起来
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        //保存到内存
//        return new InMemoryTokenStore();
        //保存到redis中
//        return new RedisTokenStore();
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
//        return daoAuthenticationProvider;
//    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

    /**
     * 对JWT进行签名的 加解密密钥
     */
//    @Bean
//    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
//        // 加载证书 读取类路径文件
//        Resource resource = new FileSystemResource("/Users/huan/code/study/idea/spring-cloud-alibaba-parent/gateway-oauth2/new-authoriza-server.jks");
//        // 创建秘钥工厂(加载读取证书数据)
//        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource, "123456".toCharArray());
//        // 读取秘钥对(公钥、私钥)
//        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("new-authoriza-server", "123456".toCharArray());
//        // 读取公钥
//        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//        // 读取私钥
//        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//
//        RSAKey rsaKey = new RSAKey.Builder(publicKey)
//                .privateKey(privateKey)
//                .keyID(UUID.randomUUID().toString())
//                .build();
//        JWKSet jwkSet = new JWKSet(rsaKey);
//        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
//    }
}