package com.tanchengjin.oauth2.conf.security.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@EnableResourceServer
@Order(8)
@Configuration
public class Oauth2ResourceServer extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        http.authorizeRequests()
                .antMatchers("/auth/**", "/oauth/token", "/articles/**","/getPublicKey","/qrcode/uuid").permitAll()
                .antMatchers("/**").access("#oauth2.hasScope('all')");
//                .anyRequest().authenticated();

    }

//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
////        resources.resourceId("f51ce644-5d92-4fe6-bee9-aa88b1708f82").stateless(true).tokenStore(tokenStore);
//        resources.tokenStore(tokenStore).accessDeniedHandler(new CustomAccessDeniedHandler()).stateless(true);
//    }
}