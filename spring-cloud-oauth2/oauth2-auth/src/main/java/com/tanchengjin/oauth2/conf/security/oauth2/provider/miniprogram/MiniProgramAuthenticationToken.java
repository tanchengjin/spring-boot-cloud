package com.tanchengjin.oauth2.conf.security.oauth2.provider.miniprogram;

import com.tanchengjin.oauth2.modules.oauth.request.MiniProgramLoginRequest;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
public class MiniProgramAuthenticationToken extends AbstractAuthenticationToken {
    private final Object credential;
    private final Object principal;

    private final MiniProgramLoginRequest request;

    public MiniProgramAuthenticationToken(MiniProgramLoginRequest request) {
        super(null);
        this.request = request;
        this.principal = null;
        this.credential = null;
        this.setAuthenticated(false);
    }

    public MiniProgramAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object credential, Object principal) {
        super(authorities);
        this.credential = credential;
        this.principal = principal;
        this.request = null;
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

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

    public MiniProgramLoginRequest getRequest() {
        return request;
    }
}
