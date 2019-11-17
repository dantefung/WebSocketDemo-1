package cn.martin6699.javaxwebsocketserver.ws.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * websocket中获取headers
 */
public class HttpHeadersConfigurator extends ServerEndpointConfig.Configurator {


    public final static String ALL_HEADERS = "ALL_HEADERS";

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        // 把headers放入ServerEndpointConfig 方便以后取用
        Map<String, List<String>> headers = request.getHeaders();
        HashMap<String, String> headersMap = new HashMap<>(10);
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            String value = null;
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                value = entry.getValue().get(0);
            }
            headersMap.put(entry.getKey(), value);
        }

        sec.getUserProperties().put(ALL_HEADERS, headersMap);

    }
}
