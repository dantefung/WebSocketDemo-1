package cn.myzf.netty.client;

import cn.myzf.netty.client.heart.NettyClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;


/**
 *  服务器启动类
  * @Author: myzf
  * @Date: 2019/2/23 13:00
  * @param
*/
@SpringBootApplication
@PropertySource(value= "classpath:/application.properties")
/*因为这个包不在默认的springboot当前目录下，需要扫描，SpringBeanFactory才可以加载到context*/
@ComponentScan(basePackages = {"cn.myzf.common.util"})
public class ClientApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ClientApplication.class, args);
		NettyClient client = new NettyClient();
		client.run();

	}




}