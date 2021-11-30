package com.ml.controller;

import com.ml.ApiResponse;
import com.ml.exception.ExceptionAdvice;
import com.ml.openfeign.TestFeignClient;
import com.ml.openfeign.TestMqFeignClient;
import com.ml.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * test controller
 *
 * @author Administrator
 * @date 2021/10/23 10:57
 */
@Slf4j
@RestController
public class TestController extends ExceptionAdvice {

    @Resource
    private TestFeignClient testFeignClient;

    @GetMapping("testSuccess")
    public ApiResponse testSuccess() {
        return ApiResponse.success();
    }

    @GetMapping("testError")
    public ApiResponse testError() {
        return ApiResponse.error();
    }

    @GetMapping("test/{id}")
    public ApiResponse test(@PathVariable(name = "id") Integer id) {
        log.info("consumer test id: {}", id);
        return ApiResponse.success(testFeignClient.test(id));
    }

    @GetMapping("test1")
    public ApiResponse test1(@NotBlank(message = "name cannot be empty") String name) {
        return ApiResponse.success(name);
    }

    @GetMapping("test2")
    public ApiResponse test2(@RequestParam(value = "name") String[] name) {
        return ApiResponse.success(name);
    }

    @Resource
    private TestMqFeignClient testMqFeignClient;

    @GetMapping("test3")
    public ApiResponse test3(@NotBlank(message = "name cannot be empty") String name) {
        return testMqFeignClient.test(name);
    }

    @GetMapping("listGetTest")
    public ApiResponse listGetTest() {
        return testFeignClient.listGetTest();
    }

    @PostMapping("test/{name}")
    public ApiResponse testPost(@PathVariable(name = "name") String name) {
        return testFeignClient.testPost(name);
    }

    @PutMapping("test/{id}/{name}")
    public ApiResponse testPut(@PathVariable(name = "id") Long id,
                               @PathVariable(name = "name") String name) {
        return testFeignClient.testPut(id, name);
    }

    @DeleteMapping("test/{id}")
    public ApiResponse testDelete(@PathVariable(name = "id") Long id) {
        return testFeignClient.testDelete(id);
    }

    @Resource
    private TestService testService;

    @GetMapping("get")
    public ApiResponse get() throws InterruptedException {
        testService.testAsync();
        log.info("get return...");
        return ApiResponse.success();
    }

}
