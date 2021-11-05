### nacos stream yaml

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
    exchange: Druid.test.lazy.Exchange
    queue: Druid.test.lazy.Queue
    key: Druid.test.lazy.Key
    delay: 10000

order:
  add:
    exchange: Druid.order.add.Exchange
    queue: Druid.order.add.Queue
    key: Druid.order.add.Key
    lazy:
      exchange: Druid.order.add.delay.Exchange
      queue: Druid.order.add.delay.Queue
      key: Druid.order.add.delay.Key
      delay: 300000

logging:
  file:
    path: /Users/ml/springcloud/nacos/druid/stream

### end