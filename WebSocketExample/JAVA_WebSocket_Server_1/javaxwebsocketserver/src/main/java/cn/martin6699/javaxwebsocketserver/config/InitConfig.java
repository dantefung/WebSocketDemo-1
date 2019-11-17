package cn.martin6699.javaxwebsocketserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Created by martin on 2019/1/22.
 */
@Configuration
public class InitConfig {

    @Bean
    public ServerEndpointExporter serverEndPointExporter() {

        return new ServerEndpointExporter();
    }

}
