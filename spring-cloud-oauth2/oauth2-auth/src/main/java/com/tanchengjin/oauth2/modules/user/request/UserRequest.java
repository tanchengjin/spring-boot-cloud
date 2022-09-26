package com.tanchengjin.oauth2.modules.user.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
public class UserRequest {
    @NotNull(groups = {UserRequest.register.class, UserRequest.login.class}, message = "用户名不允许为空")
    @NotEmpty(groups = {UserRequest.register.class, UserRequest.login.class}, message = "用户名不允许为空")
    private String username;
    @NotNull(groups = {UserRequest.register.class, UserRequest.login.class}, message = "密码不允许为空")
    @NotEmpty(groups = {UserRequest.register.class, UserRequest.login.class}, message = "密码不允许为空")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public interface register {
    }

    public interface login {
    }
}
