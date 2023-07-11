package com.tanchengjin.cms.annotations;

public enum UserLevelEnum {
    DEFAULT("default", "普通会员"),
    ADVANCED("advanced", "高级会员"),
    SUPER("super", "至尊会员"),
    ;
    private final String code;
    private final String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    UserLevelEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据code取名称
     */
    public static String getMsgByCode(String code) {
        for (UserLevelEnum anEnum : values()) {
            if (anEnum.code.equals(code)) {
                return anEnum.msg;
            }
        }
        return code;
    }
}