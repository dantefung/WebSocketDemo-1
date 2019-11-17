package cn.martin6699.javaxwebsocketclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.martin6699.javaxwebsocketclient.ws.client.WSClient;

/**
 * Created by martin on 2019/1/26.
 */
@Configuration
public class ClientConfig {

    @Bean
    public WSClient createWSClient() {

        return new WSClient("ws://127.0.0.1:9990/api/ws/1000");
    }
}
