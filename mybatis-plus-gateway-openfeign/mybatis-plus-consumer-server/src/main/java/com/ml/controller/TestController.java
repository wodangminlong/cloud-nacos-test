package com.ml.controller;

import com.ml.ApiResponse;
import com.ml.aspect.Log;
import com.ml.exception.ExceptionAdvice;
import com.ml.openfeign.TestFeignClient;
import com.ml.openfeign.TestMqFeignClient;
import com.ml.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * TestController
 *
 * @author Administrator
 * @date 2021/10/24 13:16
 */
@Slf4j
@RestController
public class TestController extends ExceptionAdvice {

    @Resource
    private TestFeignClient testFeignClient;

    @Log("get test list")
    @GetMapping("listGetTest")
    public ApiResponse listGetTest() {
        return testFeignClient.listGetTest();
    }

    @Log("test post")
    @PostMapping("test/{name}")
    public ApiResponse testPost(@PathVariable(name = "name") String name) {
        return testFeignClient.testPost(name);
    }

    @Log("test put")
    @PutMapping("test/{id}/{name}")
    public ApiResponse testPut(@PathVariable(name = "id") Long id,
                               @PathVariable(name = "name") String name) {
        return testFeignClient.testPut(id, name);
    }

    @Log("test delete id")
    @DeleteMapping("test/{id}")
    public ApiResponse testDelete(@PathVariable(name = "id") Long id) {
        return testFeignClient.testDelete(id);
    }

    @Log("test 1")
    @GetMapping("test1")
    public ApiResponse test1(@NotBlank(message = "name cannot be empty") String name) {
        return ApiResponse.success(name);
    }

    @Log("test 2")
    @GetMapping("test2")
    public ApiResponse test2(@RequestParam(value = "name") String[] name) {
        return ApiResponse.success(name);
    }

    @Resource
    private TestMqFeignClient testMqFeignClient;

    @Log("test 3")
    @GetMapping("test3")
    public ApiResponse test3(@NotBlank(message = "name cannot be empty") String name) {
        return testMqFeignClient.test(name);
    }

    @Resource
    private TestService testService;

    @Log("test 4")
    @GetMapping("test4")
    public ApiResponse test4() throws InterruptedException {
        testService.testAsync();
        log.info("test4 return success...");
        return ApiResponse.success();
    }

}
