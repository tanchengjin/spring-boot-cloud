package com.tanchengjin.oauth2.conf.security.oauth2.provider.sms;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
public class SMSAuthenticationToken extends AbstractAuthenticationToken {
    private final Object credential;
    private final Object principal;

    public SMSAuthenticationToken(String mobile, String code) {
        super(null);
        this.principal = mobile;
        this.credential = code;
        this.setAuthenticated(false);
    }

    public SMSAuthenticationToken(Object principal, Object credential, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.credential = credential;
        this.principal = principal;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credential;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
