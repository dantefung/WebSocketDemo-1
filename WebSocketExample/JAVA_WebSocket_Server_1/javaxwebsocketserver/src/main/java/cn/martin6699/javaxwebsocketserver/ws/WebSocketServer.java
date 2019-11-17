package cn.martin6699.javaxwebsocketserver.ws;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import cn.martin6699.javaxwebsocketserver.util.JsonUtil;
import cn.martin6699.javaxwebsocketserver.ws.config.HttpHeadersConfigurator;
import cn.martin6699.javaxwebsocketserver.ws.config.MartinWebSocketProperties;
import cn.martin6699.javaxwebsocketserver.ws.constant.WSMsgType;
import cn.martin6699.javaxwebsocketserver.ws.request.WSMessage;

@Component
@ServerEndpoint(
        value = "/ws/{clientId}",
        configurator = HttpHeadersConfigurator.class
)
public class WebSocketServer {

    private final static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    private final static ConcurrentHashMap<String, Session> wsSessionMap = new ConcurrentHashMap<>(
            64);

    private final static ConcurrentHashMap<String, LocalDateTime> heartbeatKeepTimeMap
            = new ConcurrentHashMap<>(64);

    private MartinWebSocketProperties webSocketProperties;

    public WebSocketServer() {
    }

    @Autowired
    public WebSocketServer(
            MartinWebSocketProperties webSocketProperties
    ) {
        this.webSocketProperties = webSocketProperties;
    }

    @OnOpen
    public void onOpen(
            @PathParam("clientId") String clientId, Session session
    ) {
        // 在这里做保存Session操作，可以用ConcurrentHashMap做clientId与Session关联保存

        // 连接打开 相当于一个心跳
        heartbeatKeepTimeMap.put(clientId, LocalDateTime.now());

        // 只允许一个客户端登录 再次登录则关闭原有的连接
        Session connectedSession = wsSessionMap.get(clientId);
        try {
            if (!Objects.isNull(connectedSession)) {
                connectedSession.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭session 设置为null 垃圾回收
            connectedSession = null;
        }

        wsSessionMap.put(clientId, session);

        logger.info("[clientId={}]-已连接ws服务器！当前在线客户端为{}", clientId, wsSessionMap.size());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(@PathParam("clientId") String clientId, Session session, String message) {
        logger.info("[clientId={}]-收到客户端消息：{}", clientId, message);
        WSMessage wsMessage = null;
        try {
            wsMessage = JsonUtil.fromJson(message, WSMessage.class);
        } catch (IOException e) {
            logger.error("解析客户端消息失败，错误信息：{}", e.getMessage());
            return;
        }
        WSMsgType wsMsgType = WSMsgType.contains(wsMessage.getWsMsgType());

        switch (wsMsgType) {

            case WS_MSG_HEART:
                logger.info("[clientId={}]-收到客户端心跳消息：{}", clientId, message);
                handleHeartbeat(wsMessage, session);
                break;

            case WS_MSG_REQUEST:
                logger.info("[clientId={}]-收到客户端业务消息：{}", clientId, message);
                handleRequest(wsMessage, session);
                break;

            case WS_MSG_ACK:
                logger.info("[clientId={}]-收到客户端ack消息：{}", clientId, message);
                break;

            default:
                logger.error("[clientId={}]-匹配不到消息类型，信息为：{}", clientId, message);
                break;
        }
    }

    private void handleRequest(WSMessage wsMessage, Session session) {

        // 处理业务逻辑


    }

    private void handleHeartbeat(WSMessage wsMessage, Session session) {
        wsMessage.setMsgTime(LocalDateTime.now().toString());
        wsMessage.setWsMsgType(WSMsgType.WS_MSG_ACK.getValue());
        String ackMsg = null;
        try {
            ackMsg = JsonUtil.toJson(wsMessage);
        } catch (JsonProcessingException e) {
            logger.error("序列化心跳对象失败，错误信息：{}", e.getMessage());

            return;
        }

        try {
            session.getBasicRemote().sendText(ackMsg);
        } catch (IOException e) {
            logger.error("发送心跳信息失败，错误信息：{}", e.getMessage());
        }
    }


    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(
            @PathParam("clientId") String clientId, Session session,
            Throwable error
    ) {
        // 不建议在发送错误调用时，关闭Websocket操作。
        error.printStackTrace();
        logger.info("[clientId={}]-error信息={}", clientId, error.getMessage());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(
            @PathParam("clientId") String clientId, Session session
    ) {
        // 无论是客户端还是服务器主动调用关闭Websocket, 都会调用该方法。这里可以做关闭后清理工作。
        Session mapSession = wsSessionMap.get(clientId);
        if (mapSession != null && !mapSession.getId().equals(session.getId())) {
            // 只有当旧的session不为空，且旧的sessionID 不等于 新的sessionID时才能移除；
            heartbeatKeepTimeMap.remove(clientId);
            wsSessionMap.remove(clientId);
            try {
                mapSession.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 发送ACK回应
     */
    private void sendAckMessage(Session session, String wsMsgResponse) {

    }

    public void sendMessage(Session session, String message) throws IOException {
        if (!session.isOpen()) {
            // 检测长连接是否正常
            return;
        }
        session.getBasicRemote().sendText(message);
    }



    public static ConcurrentHashMap<String, LocalDateTime> getHeartbeatKeepTimeMap() {
        return heartbeatKeepTimeMap;
    }

    public void checkSessionAliveAndRemove(
            String clientId, LocalDateTime heartbeatKeepTime, LocalDateTime now
    ) {
        // 时间差 单位毫秒
        Long intervalTime = Duration.between(heartbeatKeepTime, now).toMillis();
        if (intervalTime.compareTo(webSocketProperties.getHeartbeatAllowIntervalTime()) > 0) {
            // 大于心跳允许间隔时间 证明该客户端已死

            heartbeatKeepTimeMap.remove(clientId);

            try {
                wsSessionMap.get(clientId).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            wsSessionMap.remove(clientId);

            logger.warn("移除websocket, clientId=", clientId);
        }
    }
}
