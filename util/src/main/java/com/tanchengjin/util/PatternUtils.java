package com.tanchengjin.util;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
public class PatternUtils {

    /**
     * 匹配手机号
     */
    private static final String checkMobile = "^1([34578])\\d{9}$";

    /**
     * 匹配座机号
     */
    private static final String checkTelPhone = "^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$";

    /**
     * 手机号座机号通用
     */
    private static final String checkPhone = "^(((13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8})?|(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)$";
    /**
     * 校验IP地址
     */
    private static final String checkIp = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    /**
     * 校验QQ号
     */
    private static final String checkQQ = "^^[1-9]\\d{4,11}$";
    /**
     * 校验中文
     */
    private static final String checkChineseWord = "^[\\u4e00-\\u9fa5]+$";

    /**
     * 校验手机号
     *
     * @return t or f
     */
    public static boolean checkPhone(String phone) {
//        String regex = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
//                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        return phone.matches(checkPhone);
    }

    public static void main(String[] args) {
//        boolean b = PatternUtils.checkPhone("18865477815");
        String phone = "中";
        boolean b = phone.matches(checkChineseWord);
        System.out.println(b);
    }
}
