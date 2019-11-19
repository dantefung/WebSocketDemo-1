package top.jfunc.websocket.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import top.jfunc.websocket.WebSocketManager;
import top.jfunc.websocket.redis.DefaultRedisReceiver;
import top.jfunc.websocket.redis.RedisReceiver;
import top.jfunc.websocket.redis.RedisWebSocketConfig;
import top.jfunc.websocket.utils.SpringContextHolder;

import java.util.concurrent.CountDownLatch;

/**
 * 再往里面添加 listenerAdapter、RedisReceiver绑定相应的topic
 * 可以继承RedisWebSocketConfig，
 * 或者通过{@link top.jfunc.websocket.redis.EnableRedisWebSocketManager 导入}
 * 特别注意，以下这些Bean必须放到容器中
 * @author xiongshiyan at 2018/10/17 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@Configuration
public class TwoWebSocketManagerConfig extends RedisWebSocketConfig {

    @Autowired
    private RedisMessageListenerContainer redisMessageListenerContainer;

    @Bean("latchAnother")
    public CountDownLatch latchAnother() {
        return new CountDownLatch(1);
    }

    @Bean("receiverAnother")
    public RedisReceiver receiverAnother(
            @Autowired@Qualifier("latch") CountDownLatch latch) {
        return new DefaultRedisReceiver(latch){
            @Override
            protected WebSocketManager getWebSocketManager() {
                return SpringContextHolder.getBean(AnotherRedisWebSocketManager.NAME, WebSocketManager.class);
            }
        };
    }

    @Bean("listenerAdapterAnother")
    public MessageListenerAdapter listenerAdapterAnother(@Qualifier("receiverAnother") RedisReceiver receiver) {
        MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(receiver, RedisReceiver.RECEIVER_METHOD_NAME);
        redisMessageListenerContainer.addMessageListener(listenerAdapter , new PatternTopic("anotherTopic"));
        return listenerAdapter;
    }
}
