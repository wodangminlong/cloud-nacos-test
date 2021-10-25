package com.ml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Administrator
 * @date 2021/10/25 15:13
 */
@SpringBootApplication
@EnableDiscoveryClient
public class StreamServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamServerApplication.class, args);
    }

}
