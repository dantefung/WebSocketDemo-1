package cn.martin6699.javaxwebsocketserver.ws.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "martin.websocket")
@Component
@Data
public class MartinWebSocketProperties {

    private Long heartbeatAllowIntervalTime = 50000L;

    private  Long heartbeatCheckIntervalTime = 50100L;

    private Long wsWaitingIntervalTime = 2L;

}
