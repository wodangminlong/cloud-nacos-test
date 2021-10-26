package com.ml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Administrator
 * @date 2021/10/26 10:29
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MybatisPlusStreamServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusStreamServerApplication.class, args);
    }

}
