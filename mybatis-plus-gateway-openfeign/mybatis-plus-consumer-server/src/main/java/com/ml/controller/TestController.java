package com.ml.controller;

import com.ml.ApiResponse;
import com.ml.exception.ExceptionAdvice;
import com.ml.openfeign.TestFeignClient;
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

    @GetMapping("test1")
    public ApiResponse test1(@NotBlank(message = "name cannot be empty") String name) {
        return ApiResponse.success(name);
    }

    @GetMapping("test2")
    public ApiResponse test2(@RequestParam(value = "name") String[] name) {
        return ApiResponse.success(name);
    }

}
