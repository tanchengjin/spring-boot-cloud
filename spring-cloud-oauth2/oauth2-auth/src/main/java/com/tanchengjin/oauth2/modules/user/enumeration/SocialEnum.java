package com.tanchengjin.oauth2.modules.user.enumeration;

/**
 * 常用社交媒体软件API
 */
public enum SocialEnum {
    /**
     * <p>用于小程序登录</p>
     * <p>登录凭证校验。通过 wx.login 接口获得临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程</p>
     * <p>appid	string		是	小程序 appId</p>
     * <p>secret	string		是	小程序 appSecret</p>
     * <p>js_code	string		是	登录时获取的 code</p>
     * <p>grant_type	string		默认authorization_code</p>
     * {@link <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html">...</a>}
     */
    WX_AUTH_JSCODE("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code");
    private final String url;

    SocialEnum(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
