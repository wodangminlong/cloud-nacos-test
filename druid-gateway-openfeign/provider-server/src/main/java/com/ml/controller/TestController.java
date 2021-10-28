package com.ml.controller;

import com.ml.ApiResponse;
import com.ml.exception.ExceptionAdvice;
import com.ml.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * test controller
 *
 * @author Administrator
 * @date 2021/10/23 10:40
 */
@Slf4j
@RestController
public class TestController extends ExceptionAdvice {

    @Resource
    private TestService testService;

    @GetMapping("test")
    public ApiResponse test() {
        log.info("provider server is running normally");
        return ApiResponse.success();
    }

    @GetMapping("test/{id}")
    public String test(@PathVariable(name = "id") Integer id) throws SQLException {
        log.info("provider test id: {}", id);
        return testService.getTestNameById(id);
    }

    @GetMapping("listGetTest")
    public ApiResponse listGetTest() throws SQLException {
        return ApiResponse.success(testService.listGetTest());
    }

    @PostMapping("test/{name}")
    public ApiResponse testPost(@PathVariable(name = "name") String name) throws SQLException {
        int result = testService.addTest(name);
        if (result > 0) {
            return ApiResponse.success();
        }
        return ApiResponse.error();
    }

    @PutMapping("test/{id}/{name}")
    public ApiResponse testPut(@PathVariable(name = "id") Long id,
                               @PathVariable(name = "name") String name) throws SQLException {
        int result = testService.updateTest(id, name);
        if (result > 0) {
            return ApiResponse.success();
        }
        return ApiResponse.error();
    }

    @DeleteMapping("test/{id}")
    public ApiResponse testDelete(@PathVariable(name = "id") Long id) throws SQLException {
        int result = testService.deleteTest(id);
        if (result > 0) {
            return ApiResponse.success();
        }
        return ApiResponse.error();
    }

}
