### nacos provider yaml

spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/dev_test?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true&noAccessToProcedureBodies=true&testOnBorrow=true&validationQuery=select 1
    username: root
    password: root

logging:
  file:
    path: /Users/ml/springcloud/nacos/druid/provider

### end