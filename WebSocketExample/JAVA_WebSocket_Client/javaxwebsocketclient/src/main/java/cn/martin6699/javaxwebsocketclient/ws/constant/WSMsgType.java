package cn.martin6699.javaxwebsocketclient.ws.constant;

/**
 * Created by anjubao on 2018-01-23.
 */

public enum WSMsgType {
    WS_MSG_ACK(0, "应答消息"),
    WS_MSG_HEART(1, "心跳消息类型"),
    WS_MSG_REQUEST(2, "业务消息"),
    ;

    private Integer value;
    private String msg;

    WSMsgType(Integer value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public Integer getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }

    public static WSMsgType contains(Integer value) {

        for (WSMsgType type : WSMsgType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }

        return null;
    }
}
