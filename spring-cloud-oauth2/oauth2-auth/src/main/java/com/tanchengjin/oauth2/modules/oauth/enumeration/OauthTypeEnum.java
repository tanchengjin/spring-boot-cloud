package com.tanchengjin.oauth2.modules.oauth.enumeration;

/**
 * oauth_type 第三方oauth类型
 */
public enum OauthTypeEnum {
    /**
     * 小程序
     */
    MINIPROGRAM("miniprogram", "小程序");
    private final String type;

    private final String description;

    OauthTypeEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
