package com.ml.controller;

import com.ml.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * test controller
 *
 * @author dml
 * @date 2021/10/24 00:23
 */
@Slf4j
@RestController
public class TestController {

    @GetMapping("test")
    public ApiResponse test() {
        log.info("mybatis plus gateway server is running normally");
        return ApiResponse.success();
    }

}
