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
}
