package com.imooc.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @功能描述：TODO
 * @创建日期: 2019/4/8 8:01
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //通过socketChannel去获得对于的管道
        ChannelPipeline pipeline = socketChannel.pipeline();
        //通过管道添加handler
        //HttpServerCodec是由netty自己提供的助手类，当请求到服务端，我们需要做解码，响应到哭护短做编码
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());
        //添加自定义的助手类，返回“hello netty~”
        pipeline.addLast("customHandler", new CustomHandler());
    }

}
