package top.jfunc.websocket.config;

import org.springframework.data.redis.core.StringRedisTemplate;
import top.jfunc.json.impl.JSONObject;
import top.jfunc.websocket.WebSocket;
import top.jfunc.websocket.memory.MemWebSocketManager;
import top.jfunc.websocket.redis.RedisWebSocketManager;
import top.jfunc.websocket.utils.JsonUtil;

/**
 * 测试自己配置的WebSocketManager生效
 * @author xiongshiyan at 2018/10/15 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class AnotherRedisWebSocketManager extends RedisWebSocketManager{

    public static final String NAME = "anotherWebSocketManager";

    public AnotherRedisWebSocketManager(StringRedisTemplate stringRedisTemplate){
        super(stringRedisTemplate);
    }
    @Override
    public void put(String identifier, WebSocket webSocket) {
        super.put(identifier, webSocket);
        System.out.println("--------使用的是新的WebSocketManager");
    }

    @Override
    public void onMessage(String identifier, String message) {
        System.out.println(identifier + " : " + message);
        boolean isJsonObject = JsonUtil.isJsonObject(message);
        if(isJsonObject){
            JSONObject object = new JSONObject(message);
            String to = object.getString("to");
            String msg = object.getString("message");
            sendMessage(to , msg);
        }
    }

    @Override
    protected String getChannel() {
        return "anotherTopic";
    }
}
