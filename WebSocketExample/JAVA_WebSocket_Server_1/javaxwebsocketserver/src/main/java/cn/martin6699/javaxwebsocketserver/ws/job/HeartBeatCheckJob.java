package cn.martin6699.javaxwebsocketserver.ws.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.martin6699.javaxwebsocketserver.ws.WebSocketServer;

@Component
public class HeartBeatCheckJob extends QuartzJobBean  {

    private final static Logger logger = LoggerFactory.getLogger(HeartBeatCheckJob.class);

    @Autowired
    private WebSocketServer webSocketServer;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        ConcurrentHashMap<String, LocalDateTime> heartbeatKeepTimeMap = WebSocketServer.getHeartbeatKeepTimeMap();
        if (heartbeatKeepTimeMap.size() == 0) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();

        for (Map.Entry<String, LocalDateTime> entry : heartbeatKeepTimeMap.entrySet()) {

            // 定期移除过期session
            webSocketServer.checkSessionAliveAndRemove(entry.getKey(), entry.getValue(), now);
        }


    }
}
