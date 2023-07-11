package com.tanchengjin.cms.annotations.seria;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumSerialize {

    /**
     * 字段对应枚举的class
     *
     * @return
     */
    Class<?> value();

    /**
     * 获取中文名称的方法
     *
     * @return
     */
    String method() default "getMsgByCode";

}