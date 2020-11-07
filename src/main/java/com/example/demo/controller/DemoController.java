package com.example.demo.controller;

import com.example.demo.annotation.AutoLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 示例
 *
 * @author dake.luo
 * @date 2020-11-07 22:03
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    /**
     * 测试普通方法
     *
     * @param param 入参
     * @return java.lang.Object
     * @author dake.luo
     * @date 2020-11-07 22:14
     */
    @GetMapping("/test")
    @AutoLog(business = "示例", module = "测试方法", cost = true, slow = 100)
    public Object test(@RequestParam String param) {
        return String.format("Hello World, %s", param);
    }

    /**
     * 测试异常方法
     *
     * @param param 入参
     * @return java.lang.Object
     * @author dake.luo
     * @date 2020-11-07 22:14
     */
    @GetMapping("/testException")
    @AutoLog(business = "示例", module = "测试方法", cost = true, slow = 100)
    public Object testException(@RequestParam String param) {
        throw new NullPointerException(param);
    }
}
