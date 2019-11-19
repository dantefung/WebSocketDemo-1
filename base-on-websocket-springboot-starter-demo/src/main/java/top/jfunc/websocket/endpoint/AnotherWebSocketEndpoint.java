package top.jfunc.websocket.endpoint;

import org.springframework.stereotype.Component;
import top.jfunc.websocket.WebSocketManager;
import top.jfunc.websocket.config.AnotherRedisWebSocketManager;
import top.jfunc.websocket.utils.SpringContextHolder;

import javax.websocket.server.ServerEndpoint;

/**
 * 1.继承自 top.jfunc.websocket.WebSocketEndpoint
 * 2.标注@Component @ServerEndpoint
 * @author xiongshiyan
 */
@Component
@ServerEndpoint(value ="/another/websocket/connect/{identifier}")
public class AnotherWebSocketEndpoint extends top.jfunc.websocket.WebSocketEndpoint{
    @Override
    protected WebSocketManager getWebSocketManager() {
        return SpringContextHolder.getBean(AnotherRedisWebSocketManager.NAME, WebSocketManager.class);
    }
}
