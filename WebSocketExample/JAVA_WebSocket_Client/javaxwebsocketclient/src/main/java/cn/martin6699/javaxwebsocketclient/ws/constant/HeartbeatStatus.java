package cn.martin6699.javaxwebsocketclient.ws.constant;

/**
 * Created by martin on 2019/1/24.
 */
public enum  HeartbeatStatus {
    INIT(0, "心跳初始值"),
    SEND_HEARTBEAT(1, "准备发送心跳"),
    RECEIVE_HEARTBEAT_ACK(2, "等待收到心跳ACK"),
            ;

    private Integer value;
    private String msg;

    HeartbeatStatus(Integer value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public Integer getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }

}
