package com.example.demo.utils;

/**
 * 日志消息工具类
 *
 * @author dake.luo
 * @date 2020-11-07 20:32
 */
public class LogUtils {

    /** 构造方法私有化 */
    private LogUtils() {

    }

    /**
     * 简化消息，仅保留特定项目中的[类名_行号]
     *
     * @param e 错误或异常
     * @return java.lang.StringBuilder
     * @author dake.luo
     * @date 2020-11-07 11:47
     */
    public static String getSimpleExceptionMessage(Throwable e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e);
        //遍历堆栈轨迹信息
        for (StackTraceElement ste : e.getStackTrace()) {
            //获取无包名的类名
            String exClassName = ste.getClassName().replaceAll(".*\\.", "");
            //判断是否项目中的代码
            boolean isProject = ste.getClassName().startsWith("com.example")
                    && ste.getFileName() != null && ste.getFileName().startsWith(exClassName)
                    && !"AutoLogAspect".equals(exClassName);
            //定位项目中的代码
            if (isProject) {
                //拼接异常类名，去掉包名
                sb.append('[').append(exClassName);
                //拼接异常行号
                sb.append('_').append(ste.getLineNumber()).append(']');
            }
        }
        return sb.toString();
    }
}
