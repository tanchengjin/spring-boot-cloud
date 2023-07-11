package com.tanchengjin.cms.annotations.dict;

import java.lang.annotation.*;


/**
 * @author TanChengjin
 * @version v1.0.0
 * @email 18865477815@163.com
 */
@Inherited
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {
    /**
     * 注解标识值
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

    String dictTable() default "article_category";

    String dictKey() default "id";

    String dictText() default "name";
}
