package com.ankhnotes.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)//保存到运行阶段
@Target(ElementType.METHOD)//只能用于方法上
/**
 * 用于注解在controller的接口上, 当接口被调用时, 该注解的切面会使用log将相关信息输出
 */
public @interface mySystemLog {

    //接口描述信息
    String logDescription() default "";
}
