package com.tanchengjin.util;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
public class HttpUtils {
    /**
     * 移除字符串中的末尾字符/
     * @param str
     * @return
     */
    public static String prefixSub(String str)
    {
        return str.endsWith("/") ? str.substring(0, str.length() - 1) : str;
    }
}
