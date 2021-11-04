package com.ml.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

    @GetMapping("orderAdd/{orderId}/{goodId}")
    public ApiResponse orderAdd(@PathVariable(name = "orderId") String orderId,
                                @PathVariable(name = "goodId") String goodId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderId", orderId);
        jsonObject.put("goodId", goodId);
        jsonObject.put("type", 0);
        String message = JSON.toJSONString(jsonObject);
        orderMqProvider.sendMessage(message);
        orderMqProvider.sendDelayMessage(message);
        return ApiResponse.success();
    }

    @GetMapping("secKillAdd/{goodId}")
    public ApiResponse secKillAdd(@PathVariable(name = "goodId") String goodId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("goodId", goodId);
        jsonObject.put("type", 1);
        orderMqProvider.sendMessage(JSON.toJSONString(jsonObject));
        return ApiResponse.success();
    }

}
