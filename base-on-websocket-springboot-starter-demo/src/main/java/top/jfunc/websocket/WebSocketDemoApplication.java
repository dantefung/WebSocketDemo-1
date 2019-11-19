package top.jfunc.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import top.jfunc.websocket.memory.EnableMemWebSocketManager;
import top.jfunc.websocket.redis.EnableRedisWebSocketManager;
import top.jfunc.websocket.redis.action.Action;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
//@EnableMemWebSocketManager
//@EnableRedisWebSocketManager
public class WebSocketDemoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(WebSocketDemoApplication.class, args);
        Map<String, Action> beans = applicationContext.getBeansOfType(Action.class);
        System.out.println(beans);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        /**
         * war包运行的话，为了不修改配置文件，在运行的tomcat的catalina.sh中添加jvm参数-Dspring.profiles.active=test，
         * 因为我们现在已经有一个env了，所以可以根据这个设置profile
         * 命令行java -jar运行的话直接通过-Dspring.profiles.active=test来指定
         */
        setRegisterErrorPageFilter(false);
        String env = System.getProperty("env");
        if(null != env){
            Map<String , Object> map = new HashMap<>(1);
            map.put("spring.profiles.active" , env);
            builder.properties(map);
            //不能使用 builder.profiles(env);否则可能出现两个环境的都配置了
        }
        return builder.sources(WebSocketDemoApplication.class);
    }

}
