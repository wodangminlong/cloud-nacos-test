package com.ml.controller;

import com.ml.ApiResponse;
import com.ml.exception.ExceptionAdvice;
import com.ml.model.SystemLog;
import com.ml.model.SystemLogModel;
import com.ml.service.SystemLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author dml
 * @date 2021/12/7 11:11
 */
@Slf4j
@RestController
public class SystemLogController extends ExceptionAdvice {

    @Resource
    private SystemLogService systemLogService;

    @PostMapping("addSystemLog")
    public ApiResponse addSystemLog(@RequestBody SystemLogModel systemLogModel) {
        systemLogService.addSystemLog(systemLogModel);
        return ApiResponse.success();
    }

}
