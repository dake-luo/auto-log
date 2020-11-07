package com.example.demo.bean;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;


/**
 * 日志实例缓存
 *
 * @author dake.luo
 * @date 2020-11-06 21:35
 */
public class LoggerCache {

    /**
     * 日志实例记录在内存中
     */
    private static final ConcurrentHashMap<String, Logger> LOGGERS = new ConcurrentHashMap<>();

    /** 构造方法私有化 */
    private LoggerCache() {

    }

    /**
     * 根据类名获取缓存的日志实例
     *
     * @param className 包名加类名 this.getClass().getName();
     * @return org.slf4j.Logger
     * @author dake.luo
     * @date 2020-11-06 22:12
     */
    public static Logger getLoggerByClassName(String className) {
        // 从静态map中获取日志实例
        return LOGGERS.computeIfAbsent(className, k -> LoggerFactory.getLogger(className));
    }
}
