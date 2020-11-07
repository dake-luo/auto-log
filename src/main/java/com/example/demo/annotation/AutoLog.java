package com.example.demo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口出入参自动日志
 *
 * @author dake.luo
 * @date 2020-11-06 21:43
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoLog {

    /**
     * 业务名
     *
     * @return 业务名
     */
    String business() default "";

    /**
     * 模块名
     *
     * @return 模块名
     */
    String module() default "";

    /**
     * 是否打印入参
     *
     * @return 是否打印入参
     */
    boolean params() default true;

    /**
     * 是否打印出参
     *
     * @return 是否打印出参
     */
    boolean result() default true;

    /**
     * 是否输出耗时
     *
     * @return 是否输出耗时
     */
    boolean cost() default false;

    /**
     * 是否输出异常信息
     *
     * @return 是否输出异常信息
     */
    boolean exception() default true;

    /**
     * 慢日志阈值
     * <p>
     * 当值小于 0 时，不进行慢日志统计。
     * 当值大于等于0时，当前值只要大于等于这个值，就进行统计。
     *
     * @return 阈值
     */
    long slow() default -1;

}
