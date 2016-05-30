package com.gzs.learn.bootstrap.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test2")
public class TestController2 {
    @RequestMapping("a1")
    public String a1() {
        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
        }
        return "a1";
    }

    @RequestMapping("a2")
    public String a2() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
        }
        return "a2";
    }

    @RequestMapping("a3")
    public String a3() {
        return "a3";
    }
}
