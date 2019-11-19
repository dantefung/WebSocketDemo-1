package top.jfunc.websocket.endpoint;

import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;

/**
 * 1.继承自 top.jfunc.websocket.WebSocketEndpoint
 * 2.标注@Component @ServerEndpoint
 * @author xiongshiyan
 */
@Component
@ServerEndpoint(value ="/websocket/connect/{identifier}")
public class WebSocketEndpoint extends top.jfunc.websocket.WebSocketEndpoint{
}
