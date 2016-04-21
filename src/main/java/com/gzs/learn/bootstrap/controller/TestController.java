package com.gzs.learn.bootstrap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class TestController {

    @ResponseBody
    @RequestMapping("test")
    public Object test() {
        return "Hello world,this msg from spring mvc";
    }
}