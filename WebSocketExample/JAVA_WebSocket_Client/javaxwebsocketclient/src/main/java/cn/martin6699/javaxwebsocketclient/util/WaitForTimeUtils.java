package cn.martin6699.javaxwebsocketclient.util;

public class WaitForTimeUtils {

    public static void waitForMillSeconds(long millSeconds) {
        long currTime = System.currentTimeMillis();
        long tmpTime = System.currentTimeMillis();

        while (tmpTime - currTime < millSeconds) {
            tmpTime = System.currentTimeMillis();
        }
    }
}
