package com.ml.controller;

import com.ml.ApiResponse;
import com.ml.exception.ExceptionAdvice;
import com.ml.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

}
