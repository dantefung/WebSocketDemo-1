package cn.martin6699.javaxwebsocketclient.ws.client;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.websocket.ClientEndpoint;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import cn.martin6699.javaxwebsocketclient.util.JsonUtil;
import cn.martin6699.javaxwebsocketclient.util.WaitForTimeUtils;
import cn.martin6699.javaxwebsocketclient.ws.config.HeadersConfigurator;
import cn.martin6699.javaxwebsocketclient.ws.constant.HeartbeatStatus;
import cn.martin6699.javaxwebsocketclient.ws.constant.WSMsgType;
import cn.martin6699.javaxwebsocketclient.ws.request.WSMessage;

@ClientEndpoint(
        configurator = HeadersConfigurator.class
)
public class WSClient {

    private final static Logger logger = LoggerFactory.getLogger(WSClient.class);

    private URI serverURI;

    private static Session session;

    private Thread heartbeatThread;

    private static final WSMessage heartbeatReq = new WSMessage();

    private static int heartbeatStatus = HeartbeatStatus.INIT.getValue();

    static {
        heartbeatReq.setWsMsgType(WSMsgType.WS_MSG_HEART.getValue());
    }

    private WSClient() {

    }

    public WSClient(String serverURI) {
        logger.info("服务器地址为：{}", serverURI);

        this.serverURI = URI.create(serverURI);
    }

    public Thread getHeartbeatThread() {
        return heartbeatThread;
    }

    public void setHeartbeatThread(Thread heartbeatThread) {
        this.heartbeatThread = heartbeatThread;
    }

    public void startConnect() {

        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        ClientEndpointConfig endpointConfig = ClientEndpointConfig.Builder.create().build();
        ClientEndpointConfig.Configurator configurator = endpointConfig.getConfigurator();

        Map<String, List<String>> headers = new HashMap<String, List<String>>();

        configurator.beforeRequest(headers);

        boolean isConnection = false;

        while (!isConnection) {

            try {
                logger.info("开始连接。。。websocket");
                Session tempSession = container.connectToServer(this, serverURI);

                if (tempSession.isOpen()) {
                    this.session = tempSession;
                    logger.debug("URI(" + serverURI.getHost() + ")连上了");
                    isConnection = true;
                } else {
                    logger.error("URI(" + serverURI.getHost() + ")没连上了");
                }
            } catch (DeploymentException e) {
                logger.error("WSClient deploy failure , please check uri path", e.getMessage());

                return;
            } catch (IOException e) {
                logger.error("WSClient IOException", e.getMessage());
                WaitForTimeUtils.waitForMillSeconds(3 * 1000);
            }
        }

        this.heartbeatThread = new Thread(new HeartBeat());
        this.heartbeatThread.setName("Heartbeat-" + RandomStringUtils.randomNumeric(10));
        this.heartbeatThread.start();
    }

    public void restartConnect() {

        Thread oldHeartbeatThread = this.heartbeatThread;
        oldHeartbeatThread.interrupt();
        if(!oldHeartbeatThread.isInterrupted()) {
            logger.error("中断。。。");
            oldHeartbeatThread.interrupt();
        }
        stop();
        logger.info("重新连接。。。。。。");
        reInitHeartbeatReq();
        startConnect();
    }

    public void stop() {
        try {
            logger.error("========........session close........==========");
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session session) {

        logger.info("客户端已连接上服务器端");
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        // Do something with the dto
        logger.info("收到消息：{}", message);

        WSMessage wsMessage = null;
        try {
            wsMessage = JsonUtil.fromJson(message, WSMessage.class);
        } catch (IOException e) {

            logger.error("收到服务器websocket消息，json解析失败");
            return;
        }

        String msgId = wsMessage.getMsgId();
        if (msgId.isEmpty()) {
            logger.error("消息ID为空");
            return;
        }

        WSMsgType wsMsgType = WSMsgType.contains(wsMessage.getWsMsgType());
        if (wsMsgType == null) {
            logger.error("消息类型不存在");
            return;
        }

        if (wsMsgType.equals(WSMsgType.WS_MSG_ACK) && msgId.equals(heartbeatReq.getMsgId())) {

            logger.info("收到心跳ACK：{}", message);
            reInitHeartbeatReq();
            return;
        }

        if (wsMsgType.equals(WSMsgType.WS_MSG_ACK)) {

            logger.info("收到业务处理ACK：{}", message);
            return;
        }

        if (wsMsgType.equals(WSMsgType.WS_MSG_REQUEST)) {
            logger.info("收到业务处理请求：{}", message);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("客户端 发生出错，Error communicating with server: " + error.getMessage());
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("客户端已关闭websocket连接,理由：" + reason.getReasonPhrase() + ", closeCode = " +
                           reason.getCloseCode().getCode());
        restartConnect();
    }

    public void ackHandle(String wsMsgResponse, String message, String prefixLog) {
        logger.info("[{}]-收到应答消息,信息为：{}", prefixLog, message);
    }

    /**
     * 发送ACK回应
     */
    private void sendAckMessage(String wsMsgResponse) {

    }

    public void sendMessage(String message) throws IOException {
        if (!isOpen()) {
            // 检测长连接是否正常
            return;
        }
        this.session.getBasicRemote().sendText(message);
    }

    public void sendHeartbeatMessage() {
        heartbeatStatus = HeartbeatStatus.SEND_HEARTBEAT.getValue();
        LocalDateTime now = LocalDateTime.now();
        logger.info("发送心跳数据，时间：{}", now);
        String msgId = RandomStringUtils.randomNumeric(15);

        heartbeatReq.setMsgId(msgId);
        heartbeatReq.setMsgTime(now.toString());
        String heartbeatStr = null;
        try {
            heartbeatStr = JsonUtil.toJson(heartbeatReq);
            logger.info("准备发送心跳数据：" + heartbeatStr);

            try {
                if (!isOpen()) {
                    // 检测长连接是否正常
                    return;
                }
                this.session.getBasicRemote().sendText(heartbeatStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        heartbeatStatus = HeartbeatStatus.RECEIVE_HEARTBEAT_ACK.getValue();
    }

    public boolean isOpen() {
        if (Objects.isNull(session)) {
            return false;
        }

        return this.session.isOpen();
    }

    private class HeartBeat implements Runnable {

        public HeartBeat() {
        }

        @Override
        public void run() {
            logger.debug("【websocket】发送心跳线程开始");
            logger.info("*************** 心跳间隔时间等于{}秒 ***************", 30);
            while (true) {
                if (!isOpen() || !HeartbeatStatus.INIT.getValue().equals(heartbeatStatus)) {
                    logger.info("websocket未打开或者没收到心跳应答");
                    return;
                }

                sendHeartbeatMessage();

                if (!isOpen()) {
                    logger.info("websocket未打开......");
                    return;
                }
                WaitForTimeUtils.waitForMillSeconds(30 * 1000);
            }
        }
    }

    private void reInitHeartbeatReq() {
        heartbeatStatus = HeartbeatStatus.INIT.getValue();
        heartbeatReq.setMsgId(null);
        heartbeatReq.setMsgTime(null);
    }
}
