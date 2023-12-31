package com.tanchengjin.oauth2.conf.security;

import com.tanchengjin.oauth2.conf.security.oauth2.provider.MobilePasswordAuthenticationProvider;
import com.tanchengjin.oauth2.conf.security.oauth2.provider.miniprogram.MiniProgramAuthenticationProvider;
import com.tanchengjin.oauth2.conf.security.oauth2.provider.sms.SMSAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    private List<String> excludeUrl = new ArrayList<>();

    @Autowired
    private MobilePasswordAuthenticationProvider mobilePasswordAuthenticationProvider;

    @Autowired
    private SMSAuthenticationProvider smsAuthenticationProvider;

    private final MiniProgramAuthenticationProvider miniProgramAuthenticationProvider;

    public WebSecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, MiniProgramAuthenticationProvider miniProgramAuthenticationProvider) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.miniProgramAuthenticationProvider = miniProgramAuthenticationProvider;
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
////                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
////                .antMatchers("/**").permitAll()
//                .antMatchers("/articles/**").permitAll()                .anyRequest().authenticated();
//        http.csrf().disable();
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(mobilePasswordAuthenticationProvider)
                .authenticationProvider(smsAuthenticationProvider)
                .authenticationProvider(authenticationProvider())
                .authenticationProvider(miniProgramAuthenticationProvider);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

//    @Bean
//    public AuthenticationProvider mobileAuthenticationProvider()
//    {
//        return new MobilePasswordAuthenticationProvider(passwordEncoder,userService);
//    }

    //    @Bean
//    public AuthenticationProvider SMSAuthenticationProvider()
//    {
//        return new SMSAuthenticationProvider(userService);
//    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        excludeUrl.add("/getPublicKey");
        http.authorizeRequests().requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .and()
                .authorizeRequests().antMatchers(excludeUrl.toArray(new String[]{})).permitAll().anyRequest().authenticated()
                .and()
                .csrf().disable();
    }
}
