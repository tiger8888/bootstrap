package com.gzs.learn.bootstrap.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gzs.learn.bootstrap.domain.TestModel;
import com.gzs.learn.bootstrap.service.TestService;

@RestController
@RequestMapping("/")
public class TestController {
    @Resource
    private TestService testService;

    /*
     * @RequestMapping("testParallel") public String testParallel1() throws Exception { return testService.testParall();
     * }
     * @RequestMapping("testParallel2") public String testParallel2() throws Exception { return
     * testService.testParall2(); }
     * @RequestMapping("testSequence") public String testSequence() throws Exception { return
     * testService.testSequence(); }
     * @RequestMapping("testAsync") public String testAsync() throws Exception { return testService.testAsync(); }
     * @RequestMapping("testAsync2") public String testAsync2() throws Exception { return testService.testAsync2(); }
     */

    @RequestMapping(value = "testmodel", method = { RequestMethod.POST })
    public String testModel(@ModelAttribute() TestModel model) {
        return "success";
    }
}
