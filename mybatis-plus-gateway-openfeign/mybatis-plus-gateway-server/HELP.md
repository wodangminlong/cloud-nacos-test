### nacos mybatis-plus gateway yaml

spring:
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
      routes:
        - id: mybatis-plus-provider-server
        uri: lb://mybatis-plus-provider-server
        predicates:
          - Path=/provider/**
          - Method=GET,POST
        filters:
          - StripPrefix=1
        - id: mybatis-plus-consumer-server
        uri: lb://mybatis-plus-consumer-server
        predicates:
          - Path=/consumer/**
          - Method=GET,POST,PUT,DELETE
        filters:
          - StripPrefix=1

logging:
  file:
    path: /Users/ml/springcloud/nacos/mybatis-plus/gateway

### end