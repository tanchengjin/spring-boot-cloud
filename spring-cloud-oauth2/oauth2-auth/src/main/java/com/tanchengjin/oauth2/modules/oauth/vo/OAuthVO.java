package com.tanchengjin.oauth2.modules.oauth.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@Data
public class OAuthVO {

    private String name;

    private int bound = 0;

    private Date boundDate;
}
