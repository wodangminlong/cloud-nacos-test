package com.ml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Administrator
 * @date 2021/10/24 00:16
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MybatisPlusGatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusGatewayServerApplication.class, args);
    }

}
