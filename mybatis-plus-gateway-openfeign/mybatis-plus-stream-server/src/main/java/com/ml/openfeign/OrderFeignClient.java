package com.ml.openfeign;

import com.ml.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * order feign client
 *
 * @author dml
 * @date 2021/11/5 9:05
 */
@FeignClient("mybatis-plus-provider-server")
public interface OrderFeignClient {

    /**
     * add order
     *
     * @param orderId   order id
     * @param goodId    good id
     * @return  ApiResponse
     */
    @GetMapping("addOrder/{orderId}/{goodId}")
    ApiResponse addOrder(@PathVariable(name = "orderId") String orderId,
                         @PathVariable(name = "goodId") String goodId);

    /**
     * add sec kill
     *
     * @param goodId    goodId
     * @return  ApiResponse
     */
    @GetMapping("addSecKill/{goodId}")
    ApiResponse addSecKill(@PathVariable(name = "goodId") String goodId);

    /**
     * close order
     *
     * @param orderId   order id
     * @return  ApiResponse
     */
    @GetMapping("closeOrder/{orderId}")
    ApiResponse closeOrder(@PathVariable(name = "orderId") String orderId);

}
