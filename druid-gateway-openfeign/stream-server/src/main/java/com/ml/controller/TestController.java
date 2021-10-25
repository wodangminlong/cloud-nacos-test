package com.ml.controller;

import com.ml.ApiResponse;
import com.ml.exception.ExceptionAdvice;
import com.ml.provider.TestMqProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * test controller
 *
 * @author dml
 * @date 2021/10/25 15:28
 */
@Slf4j
@RestController
public class TestController extends ExceptionAdvice {

    @Resource
    private TestMqProvider testMqProvider;

    @GetMapping("test")
    public ApiResponse test() {
        log.info("stream server is running normally");
        return ApiResponse.success();
    }

    @GetMapping("test/{name}")
    public ApiResponse test(@PathVariable(name = "name") String name) {
        // test 10 seconds delay message
        testMqProvider.sendDelayMessage(name, 10 * 1000);
        return ApiResponse.success();
    }

}
