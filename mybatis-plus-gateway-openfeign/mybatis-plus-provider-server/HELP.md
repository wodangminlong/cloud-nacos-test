### nacos mybatis plus provider yaml
spring:
  autoconfigure:
    exclued: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    dynamic:
      primary: master
      datasource:
        master:
          driver-class-name: com.mysql.cj.jdbc.Driver
          url: jdbc:mysql://192.168.17.131:3306/dev_test?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true&noAccessToProcedureBodies=true&testOnBorrow=true&validationQuery=select 1
          username: test
          password: test
        slave:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://192.168.17.132:3306/dev_test?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true&noAccessToProcedureBodies=true&testOnBorrow=true&validationQuery=select 1
        username: test
        password: test
      druid:
        initialSize: 5
        minIdle: 5
        maxActive: 30
        maxWait: 60000
        timeBetweenEvictionRunsMillis: 60000
        minEvictableIdleTimeMillis: 300000
        validationQuery: SELECT 'x'
        testWhileIdle: true
        testOnBorrow: false
        testOnReturn: false
        poolPreparedStatements: true
        maxPoolPreparedStatementPerConnectionSize: 20
        filters: stat,wall,slf4j,config
        useGlobalDataSourceStat: true
        stat:
          log-slow-sql: true
          merge-sql: true
          slow-sql-millis: 10000

logging:
  level:
    com.ml.mapper: debug
  path: /home/server/logs/mybatis-plus-provider-server
### end

