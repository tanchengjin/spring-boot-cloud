package com.tanchengjin.cms.annotations.dict;

import java.lang.annotation.*;


/**
 * @author TanChengjin
 * @version v1.0.0
 * @email 18865477815@163.com
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Dict {
    Class<?> value();
    // 注解标识值
//	String value() default "";

    String method() default "getMsgByCode";
}
