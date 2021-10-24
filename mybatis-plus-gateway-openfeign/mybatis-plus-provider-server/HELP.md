### nacos mybatis plus provider yaml
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/dev_test?useSSL=false&useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=true
    username: root
    password: root

logging:
  level:
    com.ml.mapper: debug
  path: /home/server/logs/mybatis-plus-provider-server
### end

