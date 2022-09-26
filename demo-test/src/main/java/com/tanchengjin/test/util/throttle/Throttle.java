package com.tanchengjin.test.util.throttle;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Throttle {
    long second() default 60;

    int count() default 1;
}
