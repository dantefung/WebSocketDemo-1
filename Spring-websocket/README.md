# Spring-websocket
  传统的HTTP协议，一般通过向服务器发送请求，拉取数据实现半双工通信，缺点是服务器难以直接向浏览器下发消息，因此，websocket协议应运而生，可用于向建立连接的浏览器主动下发任意数据（PUSH）。本项目基于Spring平台，整合websocket协议，实现一个简易web聊天室的功能。主要特性如下:
  
  1.包含聊天室登录、退出的功能。登录时，浏览器自动向服务器发起websocket连接，退出时自动切断。
  
  2.登录后，用户可查看到聊天室在线的用户列表，我们在服务器上通过一个hashmap始终记录了当前在线的用户列表；

  3.登录的用户可以点击一个在线的其他用户，并给他发送消息，消息先提交给服务器，在通过服务器转发给另一端用户；
  
  4.支持群发消息的功能，使用时，服务器会将收到的消息群发给当前在线的所有用户;
  
  5.添加好友上线提醒和下线提醒的功能，当有好友上线或下线时自动通知所有其他在线人，不要刷新页面可看到实时在线用户列表。
  
  效果图：
![输入图片说明](https://images.gitee.com/uploads/images/2018/1212/100350_2a6aeccf_1110335.gif "SSM.gif")

![输入图片说明](http://git.oschina.net/uploads/images/2016/1121/155000_fbd7a93b_1110335.jpeg "在这里输入图片标题")
   
![输入图片说明](http://git.oschina.net/uploads/images/2016/1121/155008_ad2d6e7a_1110335.jpeg "在这里输入图片标题")

![输入图片说明](http://git.oschina.net/uploads/images/2016/1121/155016_df4cf908_1110335.jpeg "在这里输入图片标题")

![输入图片说明](http://git.oschina.net/uploads/images/2016/1121/155029_5e3afabc_1110335.jpeg "在这里输入图片标题")

### 附录：个人作品索引目录（持续更新）

#### 基础篇:职业化，从做好OA系统开始
1. [SpringMVC,Mybatis,Spring三大框架的整合实现增删改查](https://gitee.com/shenzhanwang/SSM)![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")
2. [Struts2,Hibernate,Spring三大框架的整合实现增删改查](https://gitee.com/shenzhanwang/S2SH)
3. [Spring,SpringMVC和Hibernate的整合实现增删改查](https://gitee.com/shenzhanwang/SSH)
4. [Spring平台整合activiti工作流引擎实现OA开发](https://gitee.com/shenzhanwang/Spring-activiti)![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")
5. [Spring发布与调用REST风格的WebService](https://gitee.com/shenzhanwang/Spring-REST)
6. [Spring整合Apache Shiro框架，实现用户管理和权限控制](https://gitee.com/shenzhanwang/Spring-shiro)
7. [使用Spring security做权限控制](https://gitee.com/shenzhanwang/spring-security-demo)
8. [Spring整合Jasig CAS框架实现单点登录](https://gitee.com/shenzhanwang/Spring-cas-sso)
#### 中级篇：中间件的各种姿势
9. [Spring连接mongoDB数据库实现增删改查](https://gitee.com/shenzhanwang/Spring-mongoDB)
10. [Spring连接Redis实现缓存](https://gitee.com/shenzhanwang/Spring-redis)
11. [Spring连接图存数据库Neo4j实现增删改查](https://gitee.com/shenzhanwang/Spring-neo4j)
12. [Spring平台整合消息队列ActiveMQ实现发布订阅、生产者消费者模型（JMS）](https://gitee.com/shenzhanwang/Spring-activeMQ)
13. [Spring整合消息队列RabbitMQ实现四种消息模式（AMQP）](https://gitee.com/shenzhanwang/Spring-rabbitMQ)
14. Spring框架的session模块实现集中式session管理 [购买](http://t.cn/Ai80zekN)
15. [Spring整合websocket实现即时通讯](https://gitee.com/shenzhanwang/Spring-websocket)![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")
16. 使用Spring boot整合mybatis,rabbitmq,redis,mongodb实现增删改查 [购买](http://t.cn/Ai8Yh8Oy)
17. [Spring MVC整合FastDFS客户端实现文件上传](https://gitee.com/shenzhanwang/Spring-fastdfs)
18. 23种设计模式，源码、注释、使用场景 [购买](http://t.cn/Ai8Y7tEF)
19. [使用ETL工具Kettle的实例](https://gitee.com/shenzhanwang/Kettle-demo)
20. Git指南和分支管理策略 [购买](http://t.cn/Ai8Y7948)
21. 使用数据仓库进行OLAP数据分析（Mysql+Kettle+Zeppelin） ![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")[购买](http://t.cn/Ai8Y7dVD)
#### 高级篇：架构之美
22. [zookeeper原理、架构、使用场景和可视化](https://gitee.com/shenzhanwang/zookeeper-practice)
23. Spring boot整合Apache dubbo v2.7实现分布式服务治理（SOA架构） ![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题") [购买](http://t.cn/Ai8YzoYt)
24. 使用Spring Cloud实现微服务架构（MSA架构）![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")   [购买](http://t.cn/Ai8YzrB6)
25. 使用jenkins+centos+git+maven搭建持续集成环境自动化部署分布式服务 [购买](http://t.cn/Ai8YZbaX)
26. 使用docker+compose+jenkins+gitlab+spring cloud实现微服务的编排、持续集成和动态扩容 [购买](http://t.cn/Ai8YZCYK)
27. 使用FastDFS搭建分布式文件系统（高可用、负载均衡）[购买](http://t.cn/Ai8YZePu)
28. 搭建高可用nginx集群和Tomcat负载均衡 [购买](http://t.cn/Ai8Ywlr8)
29. 搭建可扩展的ActiveMQ高可用集群 [购买](http://t.cn/Ai8YAbA8)
30. 实现Mysql数据库的主从复制、读写分离、分表分库、负载均衡和高可用 [购买](http://t.cn/Ai8YAOAK)
31. 搭建高可用redis集群实现分布式缓存 [购买](http://t.cn/Ai8Y2NQy)
32. [Spring整合Elastic search实现全文检索](https://gitee.com/shenzhanwang/Spring-elastic_search)
#### 特别篇：分布式事务和并发控制
33. 基于可靠消息最终一致性实现分布式事务（activeMQ）[购买](http://t.cn/Ai8YLPBL)
34. 使用TCC框架实现分布式事务（dubbo版）[购买](http://t.cn/Ai8YLJiN)
35. 使用TCC框架实现分布式事务（Spring Cloud版）[购买](http://t.cn/Ai8YLnVP)
36. 决战高并发：数据库锁机制和事务隔离级别的实现![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题") [购买](http://t.cn/Ai8YyAQE)
37. 决战高并发：使用redis实现分布式锁  ![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")[购买](http://t.cn/Ai8Y4bER)
38. 决战高并发：使用zookeeper实现分布式锁 [购买](http://t.cn/Ai8Y4Cuq)
39. 决战高并发：Java多线程编程实例 [购买](http://t.cn/Ai8Y4s0r)
40. 决战高并发：使用netty实现高性能NIO通信 [购买](http://t.cn/Ai8Ybq3e)

### 快捷入口
[我的网店](http://t.cn/Ai8YycFz)

[购买全套](http://t.cn/Ai8YG9m4)