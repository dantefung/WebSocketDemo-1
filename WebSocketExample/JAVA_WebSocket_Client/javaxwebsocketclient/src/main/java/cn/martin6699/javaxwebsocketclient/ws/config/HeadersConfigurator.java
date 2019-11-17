package cn.martin6699.javaxwebsocketclient.ws.config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.websocket.ClientEndpointConfig;

public class HeadersConfigurator extends ClientEndpointConfig.Configurator {
    @Override
    public void beforeRequest(Map<String, List<String>> headers) {


        headers.put("clientName", Arrays.asList("martin6699"));
    }
}
