package com.ml.openfeign;

import com.ml.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * test feign client
 *
 * @author Administrator
 * @date 2021/10/23 10:59
 */
@FeignClient("provider-server")
public interface TestFeignClient {

    /**
     * test
     *
     * @param id id
     * @return  string
     */
    @GetMapping(value = "/test/{id}")
    String test(@PathVariable(name = "id") Integer id);

    /**
     * listGetTest
     *
     * @return  ApiResponse
     */
    @GetMapping(value = "/listGetTest")
    ApiResponse listGetTest();

    /**
     * test post
     *
     * @param name  name
     * @return  ApiResponse
     */
    @PostMapping("test/{name}")
    ApiResponse testPost(@PathVariable(name = "name") String name);

    /**
     * test put
     *
     * @param id    id
     * @param name  name
     * @return  ApiResponse
     */
    @PutMapping("test/{id}/{name}")
    ApiResponse testPut(@PathVariable(name = "id") Long id,
                        @PathVariable(name = "name") String name);

    /**
     * test delete
     *
     * @param id    id
     * @return  ApiResponse
     */
    @DeleteMapping("test/{id}")
    ApiResponse testDelete(@PathVariable(name = "id") Long id);

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
     * addSecKill
     *
     * @param goodId    good id
     * @return  ApiResponse
     */
    @GetMapping("addSecKill/{goodId}")
    ApiResponse addSecKill(@PathVariable(name = "goodId") String goodId);

}
