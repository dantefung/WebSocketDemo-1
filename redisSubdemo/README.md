# redissubdemo
springboot入门--springboot集成redis实现消息发布订阅模式
TestController：负责触发redis 消息发送；
RedisConfig ：配置监听redis订阅消息容器包括订阅主题
以及重写消息适配器并绑定消息处理器，利用反射调用消息接收容器里的处理方法

## 测试多个节点
```
 java -jar listendemo-0.0.1-SNAPSHOT.jar
 java -jar listendemo-0.0.1-SNAPSHOT.jar --server.port=9001
 java -jar listendemo-0.0.1-SNAPSHOT.jar --server.port=9002
 java -jar listendemo-0.0.1-SNAPSHOT.jar --server.port=9002


```

## 测试发送消息
```
http://localhost:8080/apis/sendRedisMsg

```