package cn.myzf.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import cn.myzf.server.typeServer.TCPServer;


/**
 *  服务器启动类
  * @Author: myzf
  * @Date: 2019/2/23 13:00
  * @param
*/
@SpringBootApplication
@PropertySource(value= "classpath:/application.properties")
public class ServerApplication {

	public static void main(String[] args) throws Exception {

		//SpringApplication.run(ServerApplication.class, args);
		 ConfigurableApplicationContext context = SpringApplication.run(ServerApplication.class, args);
		 TCPServer tcpServer = context.getBean(TCPServer.class);
         tcpServer.start();
		System.out.println("打开监控Url：http://localhost:10003/monitor.do");
	}




}