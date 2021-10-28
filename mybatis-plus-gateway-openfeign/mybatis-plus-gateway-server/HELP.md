### nacos gateway yaml

spring:
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
      routes:
        - id: consumer-server
        uri: lb://consumer-server
        predicates:
          - Path=/consumer/**
          - Method=GET,POST,PUT,DELETE
        filters:
          - StripPrefix=1

logging:
  file:
    path: /Users/ml/springcloud/nacos/mybatis-plus/gateway

### end