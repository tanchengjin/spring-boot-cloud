package com.tanchengjin.oauth2.modules.user.enumeration;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
public enum UserStatusEnum {
    /**
     * oauth用户
     */
    OAUTH(-2, "oauth用户");

    private final int status;
    private final String text;

    UserStatusEnum(int status, String text) {
        this.status = status;
        this.text = text;
    }


    public int getStatus() {
        return status;
    }

    public String getText() {
        return text;
    }
}
