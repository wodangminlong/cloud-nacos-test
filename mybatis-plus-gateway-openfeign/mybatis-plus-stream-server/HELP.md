### nacos mybatis-plus stream yaml

spring:  
  rabbitmq:
    host: 192.168.17.131
    port: 5672
    username: admin
    password: admin
    virtual-host: /
    listener:
      simple:
        acknowledge-mode: manual

test:
  lazy:
    exchange: MybatisPlus.test.lazy.Exchange
    queue: MybatisPlus.test.lazy.Queue
    key: MybatisPlus.test.lazy.Key
    delay: 10000

order:
  add:
    exchange: MybatisPlus.order.add.Exchange
    queue: MybatisPlus.order.add.Queue
    key: MybatisPlus.order.add.Key
    lazy:
      exchange: MybatisPlus.order.add.delay.Exchange
      queue: MybatisPlus.order.add.delay.Queue
      key: MybatisPlus.order.add.delay.Key
      delay: 300000

logging:
  file:
    path: /Users/ml/springcloud/nacos/mybatis-plus/mybatis-plus-stream

### end