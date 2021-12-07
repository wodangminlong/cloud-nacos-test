package com.ml.controller;

import com.ml.ApiResponse;
import com.ml.exception.ExceptionAdvice;
import com.ml.model.SystemLog;
import com.ml.service.SystemLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author dml
 * @date 2021/12/7 9:42
 */
@Slf4j
@RestController
public class SystemLogController extends ExceptionAdvice {

    @Resource
    private SystemLogService systemLogService;

    @PostMapping("addSystemLog")
    public ApiResponse addSystemLog(@RequestBody SystemLog systemLog) {
        systemLogService.addSystemLog(systemLog);
        return ApiResponse.success();
    }

}
