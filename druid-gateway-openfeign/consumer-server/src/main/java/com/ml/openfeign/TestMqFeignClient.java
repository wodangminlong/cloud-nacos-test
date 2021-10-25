package com.ml.openfeign;

import com.ml.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * test mq feign client
 *
 * @author dml
 * @date 2021/10/25 15:46
 */
@FeignClient("stream-server")
public interface TestMqFeignClient {

    /**
     * test
     *
     * @param name name
     * @return  string
     */
    @GetMapping(value = "/test/{name}")
    ApiResponse test(@PathVariable(name = "name") String name);

}
