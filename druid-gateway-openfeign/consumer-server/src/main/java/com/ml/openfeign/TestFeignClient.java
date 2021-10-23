package com.ml.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

}
