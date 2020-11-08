package com.example.demo.aop;

import cn.hutool.json.JSONUtil;
import com.example.demo.annotation.AutoLog;
import com.example.demo.bean.LoggerCache;
import com.example.demo.utils.LogUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 日志AOP
 *
 * @author dake.luo
 * @date 2020-11-06 21:40
 */
@Aspect
@Component
public class AutoLogAspect {

    /**
     * 加入注解自动记录方法日志
     *
     * @param joinPoint 连接点
     * @param autoLog   日志注解
     * @return java.lang.Object
     * @author dake.luo
     * @date 2020-11-06 21:49
     */
    @Around(value = "@annotation(autoLog)")
    public Object logAround(ProceedingJoinPoint joinPoint, AutoLog autoLog) throws Throwable {
        //获取执行方法的类的名称（包名加类名）
        String className = joinPoint.getTarget().getClass().getName();
        //获取实例
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取方法
        Method method = signature.getMethod();
        //获取方法名
        String methodName = method.getName();
        //从缓存中获取日志实例
        Logger log = LoggerCache.getLoggerByClassName(className);
        try {
            log.info("{};{};===>{}.{}() 执行", autoLog.business(), autoLog.module(), className, methodName);
            //获取入参
            Object[] args = joinPoint.getArgs();
            //转换json
            String argsJsonStr = JSONUtil.toJsonStr(args);
            //1、是否打印入参
            if (autoLog.params()) {
                log.info("{} 请求参数  ===>  {}", methodName, argsJsonStr);
            }
            //获取当前时间
            long start = System.currentTimeMillis();
            //2、执行方法获取返回值
            Object proceed = joinPoint.proceed();
            //获取慢日志阈值
            long slowThresholdMills = autoLog.slow();
            //3、是否打印耗时或慢日志警告
            if (autoLog.cost() || slowThresholdMills > 0) {
                //计算耗时
                long costTime = System.currentTimeMillis() - start;
                //如果打印耗时
                if (autoLog.cost()) {
                    log.info("{} 执行耗时 ===> {}ms", methodName, costTime);
                }
                //如果打印慢日志警告
                if (slowThresholdMills > 0 && costTime >= slowThresholdMills) {
                    log.warn("{} 慢日志  ===>  {}ms >= {}ms", methodName, costTime, slowThresholdMills);
                }
            }
            //4、是否打印出参
            if (autoLog.result()) {
                //转换json
                String proceedJsonStr = JSONUtil.toJsonStr(proceed);
                log.info("{} 返回参数  ===>  {}", methodName, proceedJsonStr);
            }
            //返回
            return proceed;
        } catch (Throwable e) {
            //5、是否记录异常
            if (autoLog.exception()) {
                log.error("{} 发生异常  ===>  {}", methodName, LogUtils.getSimpleExceptionMessage(e));
            }
            throw e;
        }
    }


}
