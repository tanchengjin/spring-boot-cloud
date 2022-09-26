package com.tanchengjin.gateway.constant;

/**
 * 网关常量定义
 */
public class GatewayConstant {
    /**
     * 注册uri
     */
    public static final String LOGIN_URI = "/e-commerce/login";
    /**
     * 注册uri
     */
    public static final String REGISTER_URI = "/e-commerce/register";

    /**
     * 去授权中心拿到登录token的uri格式化接口
     */
    public static final String AUTHORITY_CENTER_TOKEN_URL_FORMAT =
            "http://%s:%s/authentication/auth/login";

    /**
     * 授权中心注册接口
     */
    public static final String AUTHORITY_CENTER_REGISTER_URL_FORMAT =
            "http://%s:%s/authentication/auth/register";
}
