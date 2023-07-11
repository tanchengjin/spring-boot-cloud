package com.tanchengjin.oauth2.modules.oauth.enumeration;

/**
 * oauth_type 第三方oauth类型
 */
public enum OauthTypeEnum {
    /**
     * 小程序
     */
    MINIPROGRAM("miniprogram", "小程序"),
    GITEE("gitee", "Gitee"),
    WEIBO("weibo", "新浪微博");
    private final String type;

    private final String name;

    OauthTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
