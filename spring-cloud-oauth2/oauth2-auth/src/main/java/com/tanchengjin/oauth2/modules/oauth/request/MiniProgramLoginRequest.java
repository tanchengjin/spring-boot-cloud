package com.tanchengjin.oauth2.modules.oauth.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
@Data
public class MiniProgramLoginRequest implements OauthLoginRequest {
    @NotBlank
    private String code;

    private UserInfo userInfo;

    @Data
    public static class UserInfo {
        //用户头像
        private String avatarUrl;
        //oauth中的用户名
        private String nickName;
        //性别,0未知、1男、2女
        private int gender;
    }
}
