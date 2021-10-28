### nacos provider yaml

spring:
  datasource:
    master:
      url: jdbc:mysql://192.168.17.131:3306/dev_test?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true&noAccessToProcedureBodies=true&testOnBorrow=true&validationQuery=select 1
      username: test
      password: test
    slave:
      url: jdbc:mysql://192.168.17.132:3306/dev_test?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true&noAccessToProcedureBodies=true&testOnBorrow=true&validationQuery=select 1
      username: test
      password: test

logging:
  file:
    path: /Users/ml/springcloud/nacos/druid/provider

### end