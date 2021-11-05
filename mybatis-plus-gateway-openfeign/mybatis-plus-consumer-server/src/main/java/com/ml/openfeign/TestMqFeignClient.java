package com.ml.openfeign;

import com.ml.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Administrator
 * @date 2021/10/26 10:43
 */
@FeignClient("mybatis-plus-stream-server")
public interface TestMqFeignClient {

    /**
     * test
     *
     * @param name name
     * @return  string
     */
    @GetMapping(value = "/test/{name}")
    ApiResponse test(@PathVariable(name = "name") String name);

    /**
     * order add
     *
     * @param orderId   orderId
     * @param goodId    goodId
     * @return  ApiResponse
     */
    @GetMapping("orderAdd/{orderId}/{goodId}")
    ApiResponse orderAdd(@PathVariable(name = "orderId") String orderId,
                         @PathVariable(name = "goodId") String goodId);

    /**
     * secKill add
     *
     * @param goodId    good id
     * @return  ApiResponse
     */
    @GetMapping("secKillAdd/{goodId}")
    ApiResponse secKillAdd(@PathVariable(name = "goodId") String goodId);

}
