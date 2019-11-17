package cn.martin6699.javaxwebsocketclient.runner;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import cn.martin6699.javaxwebsocketclient.ws.client.WSClient;

@Component
public class WebSocketApplicationRunner implements ApplicationRunner {

    private final static Logger logger = LoggerFactory.getLogger(WebSocketApplicationRunner.class);
    @Autowired
    private WSClient wsClient;


    @Override
    public void run(ApplicationArguments args) throws Exception {
    logger.info("开始启动websocket....");
    wsClient.startConnect();
        logger.info("启动websocket完成....");
    }
}
