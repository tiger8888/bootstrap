package com.gzs.learn.bootstrap.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gzs.learn.bootstrap.service.TestService;

@RestController
@RequestMapping("/")
public class TestController {
    @Resource
    private TestService testService;

    @RequestMapping("testParallel")
    public String testParallel() throws Exception {
        return testService.testParall();
    }

    @RequestMapping("testSequence")
    public String testSequence() throws Exception {
        return testService.testSequence();
    }

    @RequestMapping("testAsync")
    public String testAsync() throws Exception {
        return testService.testAsync();
    }
}
