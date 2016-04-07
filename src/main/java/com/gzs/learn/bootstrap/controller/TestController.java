package com.gzs.learn.bootstrap.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class TestController {

    @ResponseBody
    @RequestMapping("test")
    public Object test() {
        Map<String, Object> map = new HashMap<>();
        map.put("key", "val");
        return map;
    }
}
