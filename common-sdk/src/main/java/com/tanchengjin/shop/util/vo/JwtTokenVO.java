package com.tanchengjin.shop.util.vo;

import lombok.Data;

public class JwtTokenVO {
    private String token = "";
    private String type = "Bearer ";

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
