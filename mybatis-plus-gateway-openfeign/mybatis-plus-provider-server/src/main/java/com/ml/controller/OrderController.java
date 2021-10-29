package com.ml.controller;

import com.ml.ApiResponse;
import com.ml.exception.ExceptionAdvice;
import com.ml.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * order controller
 *
 * @author dml
 * @date 2021/10/29 15:39
 */
@Slf4j
@RestController
public class OrderController extends ExceptionAdvice {

    @Resource
    private OrderService orderService;

    @GetMapping("addOrder/{orderId}/{goodId}")
    public ApiResponse addOrder(@PathVariable(name = "orderId") String orderId,
                                @PathVariable(name = "goodId") String goodId) {
        boolean addOrderSuccess = orderService.addOrderInfo(orderId, goodId);
        if (addOrderSuccess) {
            return ApiResponse.success();
        }
        return ApiResponse.error();
    }

    @GetMapping("addSecKill/{goodId}")
    public ApiResponse addSecKill(@PathVariable(name = "goodId") String goodId) {
        int result = orderService.addSecKillInfo(goodId);
        if (result > 0) {
            return ApiResponse.success();
        }
        return ApiResponse.error();
    }

}
