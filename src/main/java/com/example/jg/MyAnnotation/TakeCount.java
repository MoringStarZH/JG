package com.example.gfjc.MyAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @title Count
 * @Author: ZKY
 * @CreateTime: 2024-04-08  11:42
 * @Description: TODO
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TakeCount {

    //表示统计接口次数的时间，默认为60秒
    int time() default 60;

}
