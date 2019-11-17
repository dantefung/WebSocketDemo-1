package cn.martin6699.javaxwebsocketclient.ws.exception;

public class DisconnectException extends RuntimeException {

    public DisconnectException(String message) {
        super(message);
    }

    public DisconnectException(String message, Throwable cause) {
        super(message, cause);
    }
}
