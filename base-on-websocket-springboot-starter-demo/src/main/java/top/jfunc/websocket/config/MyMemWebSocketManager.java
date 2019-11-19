package top.jfunc.websocket.config;

import top.jfunc.websocket.WebSocket;
import top.jfunc.websocket.memory.MemWebSocketManager;

/**
 * 测试自己配置的WebSocketManager生效
 * @author xiongshiyan at 2018/10/15 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class MyMemWebSocketManager extends MemWebSocketManager{
    @Override
    public void put(String identifier, WebSocket webSocket) {
        super.put(identifier, webSocket);
        System.out.println("--------使用的是新的WebSocketManager");
    }
}
