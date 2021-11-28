package com.ml;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Administrator
 * @date 2021/10/24 00:26
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@MapperScan("com.ml.mapper")
public class MybatisPlusProviderServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusProviderServerApplication.class, args);
    }

}
