package com.tanchengjin.oauth2.modules.oauth.enumeration;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
public enum QRCodeStateEnum {
    PENDING(0, "等待扫码"),

    CONFIRM(1, "待确认"),

    AUTHORIZATION(2, "已授权"),
    FINISH(3, "已登录"),

    NONEXISTENT(-1, "二维码不存在或已过期");
    private final int code;
    private final String text;


    QRCodeStateEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    /**
     * 根据code获取去value
     *
     * @param code
     * @return
     */
    public static String getTextByCode(int code) {
        for (QRCodeStateEnum qrCodeStateEnum : QRCodeStateEnum.values()) {
            if (qrCodeStateEnum.getCode() == code) {
                return qrCodeStateEnum.getText();
            }
        }
        return "位置状态";
    }
}
