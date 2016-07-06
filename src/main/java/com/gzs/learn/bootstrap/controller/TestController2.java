package com.gzs.learn.bootstrap.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gzs.learn.bootstrap.util.ApplicationUtil;

@RestController
@RequestMapping("test2")
public class TestController2 {
    private static final int A1_SLEEP = Integer.parseInt(ApplicationUtil.getInstance().getProperty("a1_sleep"));
    private static final int A2_SLEEP = Integer.parseInt(ApplicationUtil.getInstance().getProperty("a2_sleep"));
    private static final int A3_SLEEP = Integer.parseInt(ApplicationUtil.getInstance().getProperty("a3_sleep"));
    private static final int A4_SLEEP = Integer.parseInt(ApplicationUtil.getInstance().getProperty("a4_sleep"));

    @RequestMapping("a1")
    public String a1() {
        try {
            Thread.sleep(A1_SLEEP);
        } catch (InterruptedException e) {
        }
        return "a1";
    }

    @RequestMapping("a2")
    public String a2() {
        try {
            Thread.sleep(A2_SLEEP);
        } catch (InterruptedException e) {
        }
        return "a2";
    }

    @RequestMapping("a3")
    public String a3() {
        try {
            Thread.sleep(A3_SLEEP);
        } catch (InterruptedException e) {
        }
        return "a3";
    }

    @RequestMapping("a4")
    public String a4() {
        try {
            Thread.sleep(A4_SLEEP);
        } catch (InterruptedException e) {
        }
        return "a4";
    }

    @RequestMapping("a5")
    public String a5() {
        return "a5";
    }
}
