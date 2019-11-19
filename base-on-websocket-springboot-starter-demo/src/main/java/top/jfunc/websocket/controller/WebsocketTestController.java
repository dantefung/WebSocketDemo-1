package top.jfunc.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import top.jfunc.websocket.WebSocketManager;
import top.jfunc.websocket.config.AnotherRedisWebSocketManager;

/**
 * @author xiongshiyan at 2018/10/10 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@RestController
@RequestMapping("/websocket/test")
public class WebsocketTestController {
    @Autowired
    @Qualifier(WebSocketManager.WEBSOCKET_MANAGER_NAME)
    private WebSocketManager webSocketManager;

    @Autowired
    @Qualifier(AnotherRedisWebSocketManager.NAME)
    private WebSocketManager anotherWebSocketManager;

    @GetMapping("/get/send")
    public String sendGet(@RequestParam("token") String token ,
                       @RequestParam("message") String message) throws Exception{
        webSocketManager.sendMessage(token , message);
        return ("发送成功");
    }
    @PostMapping("post/send")
    public String sendPost(@RequestParam("token") String token , @RequestBody String body) throws Exception{
        webSocketManager.sendMessage(token , body);
        return ("发送成功");
    }
    @PostMapping("broadcast")
    public String broadcast(@RequestBody String body) throws Exception{
        webSocketManager.broadcast(body);
        return ("发送成功");
    }




    @PostMapping("post/send/another")
    public String sendPostAnother(@RequestParam("token") String token , @RequestBody String body) throws Exception{
        anotherWebSocketManager.sendMessage(token , body);
        return ("发送成功");
    }
    @PostMapping("broadcast/another")
    public String broadcastAnother(@RequestBody String body) throws Exception{
        anotherWebSocketManager.broadcast(body);
        return ("发送成功");
    }





    @GetMapping("clients")
    public String getClientsNum(){
        return ("目前在线：" + webSocketManager.size());
    }
}
