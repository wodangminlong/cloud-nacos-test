package com.ml.controller;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.ml.ApiErrorCode;
import com.ml.ApiResponse;
import com.ml.exception.ExceptionAdvice;
import com.ml.openfeign.TestMqFeignClient;
import com.ml.util.OrderIdUtils;
import com.ml.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * redis test controller
 *
 * @author dml
 * @date 2021/10/29 15:36
 */
@Slf4j
@RestController
@RequestMapping("/redis/")
public class RedisTestController extends ExceptionAdvice {

    @Resource
    private RedisUtils redisUtils;

    /**
     * redis test set
     *
     * @param id     id
     * @param name   name
     * @param expire expire
     * @return ApiResponse
     */
    @GetMapping("set/{id}/{name}/{expire}")
    public ApiResponse redisTestSet(@PathVariable(name = "id") String id,
                                    @PathVariable(name = "name") String name,
                                    @PathVariable(name = "expire") long expire) {
        redisUtils.expire(id, name, expire);
        return ApiResponse.success();
    }

    /**
     * redis test get
     *
     * @param id id
     * @return ApiResponse
     */
    @GetMapping("get/{id}")
    public ApiResponse redisTestGet(@PathVariable(name = "id") String id) {
        String name = redisUtils.get(id);
        return ApiResponse.success(name);
    }

    /**
     * redis test increment
     *
     * @param id        id
     * @param incrValue incrValue
     * @return ApiResponse
     */
    @GetMapping("incr/{id}/{incrValue}")
    public ApiResponse redisTestIncrement(@PathVariable(name = "id") String id,
                                          @PathVariable(name = "incrValue") long incrValue) {
        Long incrResult = redisUtils.incr(id, incrValue);
        return ApiResponse.success(incrResult);
    }

    /**
     * redis test decrement
     *
     * @param id        id
     * @param decrValue decrValue
     * @return ApiResponse
     */
    @GetMapping("decr/{id}/{decrValue}")
    public ApiResponse redisTestDecrement(@PathVariable(name = "id") String id,
                                          @PathVariable(name = "decrValue") long decrValue) {
        Long decrResult = redisUtils.decr(id, decrValue);
        return ApiResponse.success(decrResult);
    }

    /**
     * redis test delete
     *
     * @param id id
     * @return ApiResponse
     */
    @GetMapping("delete/{id}")
    public ApiResponse redisTestDelete(@PathVariable(name = "id") String id) {
        redisUtils.delete(id);
        return ApiResponse.success();
    }

    @Resource
    private TestMqFeignClient testMqFeignClient;

    @Resource
    private OrderIdUtils orderIdUtils;

    @GetMapping("good/{id}")
    public ApiResponse goodInit(@PathVariable(name = "id") String id) {
        int initNum = 10;
        redisUtils.expire(id, String.valueOf(initNum), 60 * 60 * 24L);
        return ApiResponse.success();
    }

    @GetMapping("good/{id}/{num}")
    public ApiResponse goodAdd(@PathVariable(name = "id") String id,
                               @PathVariable(name = "num") Long num) {
        Long goodAddResult = redisUtils.incr(id, num);
        return ApiResponse.success(goodAddResult);
    }

    @GetMapping("order/{goodId}")
    public ApiResponse redisTestOrder(@PathVariable(name = "goodId") String goodId) {
        String surplusGoodsNumStr = redisUtils.get(goodId);
        if (StringUtils.isNotBlank(surplusGoodsNumStr) && Long.parseLong(surplusGoodsNumStr) <= 0) {
            return soldOut(goodId);
        }
        long timeout = 1500L;
        long start = System.currentTimeMillis();
        String keyPrefix = "order_";
        try {
            for (; ; ) {
                // try to get lock
                boolean getLock = redisUtils.lock(keyPrefix + goodId, keyPrefix + goodId, 500 * 1000L);
                if (getLock) {
                    log.info("get lock");
                    long surplusGoodsNum = redisUtils.decr(goodId, 1L);
                    if (surplusGoodsNum < 0) {
                        redisUtils.set(goodId, String.valueOf(0));
                        return soldOut(goodId);
                    }
                    log.info("user get goods , surplusGoodsNum: {}", surplusGoodsNum);
                    return testMqFeignClient.orderAdd(orderIdUtils.getOrderId(), goodId);
                }
                long end = System.currentTimeMillis() - start;
                if (end >= timeout) {
                    return ApiResponse.error(ApiErrorCode.OPERATION_IS_TOO_FREQUENT);
                }
            }
        } finally {
            redisUtils.releaseLock(keyPrefix + goodId);
        }
    }

    /**
     * sold out
     *
     * @param goodId good id
     * @return ApiResponse
     */
    private ApiResponse soldOut(String goodId) {
        log.info("the goods have been sold out");
        ApiResponse apiResponse = testMqFeignClient.secKillAdd(goodId);
        if (apiResponse.getCode() == ApiErrorCode.SUCCESS.getCode()) {
            return ApiResponse.error(ApiErrorCode.THE_GOODS_HAVE_BEEN_SOLD_OUT);
        }
        return apiResponse;
    }

}
