package com.tanchengjin.shop.vo;


import javax.validation.constraints.NotNull;

public class LoginOrRegisterVO {
    @NotNull(message = "用户名不允许为空", groups = {Login.class, Register.class})
    private String username;
    @NotNull(message = "密码不允许为空", groups = {Login.class, Register.class})
    private String password;

    public interface Login {
    }

    public interface Register {
    }
}
