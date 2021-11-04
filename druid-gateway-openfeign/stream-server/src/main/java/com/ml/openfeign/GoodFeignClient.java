package com.ml.openfeign;

import com.ml.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * good feign client
 *
 * @author dml
 * @date 2021/11/4 10:53
 */
@FeignClient("consumer-server")
public interface GoodFeignClient {

    /**
     * good add
     *
     * @param id  id
     * @param num   num
     * @return  ApiResponse
     */
    @GetMapping("redis/good/{id}/{num}")
    ApiResponse goodAdd(@PathVariable(name = "id") String id,
                        @PathVariable(name = "num") Long num);

}
