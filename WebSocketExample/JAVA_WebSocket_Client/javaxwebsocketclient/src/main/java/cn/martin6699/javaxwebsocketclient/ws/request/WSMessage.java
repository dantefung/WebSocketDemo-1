package cn.martin6699.javaxwebsocketclient.ws.request;

import lombok.Data;

/**
 * Created by martin on 2019/1/24.
 */
@Data
public class WSMessage {

    private Integer wsMsgType;

    private String msg;

    private String msgId;

    private String msgTime;

}
