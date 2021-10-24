package com.ml.openfeign;

import com.ml.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * test feign client
 *
 * @author Administrator
 * @date 2021/10/24 10:59
 */
@FeignClient("mybatis-plus-provider-server")
public interface TestFeignClient {

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

}
