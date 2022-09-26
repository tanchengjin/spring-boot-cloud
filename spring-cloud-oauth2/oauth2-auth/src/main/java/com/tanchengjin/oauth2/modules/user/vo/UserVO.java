package com.tanchengjin.oauth2.modules.user.vo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@Data
public class UserVO implements UserDetails {
    private String username;

    private String password;

    private String role = "";

    private int status;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(role);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status >= 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status >= 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status >= 1;
    }

    @Override
    public boolean isEnabled() {
        return status >= 1;
    }
}
