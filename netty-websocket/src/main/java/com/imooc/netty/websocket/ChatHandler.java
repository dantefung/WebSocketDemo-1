package com.imooc.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * @功能描述：处理消息的handler
 * TextWebSocketFrame: 在netty中，是用于为websocket专门处理文本的对象，frame是消息的载体
 * @创建日期: 2019/4/8 9:16
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    //用于记录和管理所有客户端的channel
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //获取客户端传输过来的消息
        String content = msg.text();
        System.out.println("接收到的数据：" + content);
//        for(Channel channel : clients){
//            channel.writeAndFlush(new TextWebSocketFrame("[服务器在]" + LocalDateTime.now() + "接收到消息, 消息为：" + content));
//        }
        //下面这个方法，和上面的for循环一致
        clients.writeAndFlush(new TextWebSocketFrame("[服务器在]" + LocalDateTime.now() + "接收到消息, 消息为：" + content));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //当客户端连接服务端的时候，获取客户端的channel，并且放到ChannelGroup中去进行管理
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //当初发handlerRemoved， ChannelGroup会自动移除对应客户端的channel
//        clients.remove(ctx.channel());
        System.out.println("客户端断开，channel对应的长id为：" + ctx.channel().id().asLongText());
        System.out.println("客户端断开，channel对应的短id为：" + ctx.channel().id().asShortText());
    }
}
