package com.ml.controller;

import com.ml.ApiResponse;
import com.ml.exception.ExceptionAdvice;
import com.ml.provider.OrderMqProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * OrderController
 *
 * @author dml
 * @date 2021/11/02 21:49
 */
@Slf4j
@RestController
public class OrderController extends ExceptionAdvice {

    @Resource
    private OrderMqProvider orderMqProvider;

    @GetMapping("orderAdd/{name}")
    public ApiResponse orderAdd(@PathVariable(name = "name") String name) {
        orderMqProvider.sendDelayMessage(name);
        return ApiResponse.success();
    }

}
