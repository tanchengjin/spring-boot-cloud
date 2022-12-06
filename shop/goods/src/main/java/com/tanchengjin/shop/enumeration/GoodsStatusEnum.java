package com.tanchengjin.shop.enumeration;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
public enum GoodsStatusEnum {
    UNSOLD(-1, "已下架"),
    SOLD(1, "正常");
    private final int code;
    private final String message;

    GoodsStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
