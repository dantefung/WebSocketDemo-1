package com.imooc.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @功能描述：TODO
 * @创建日期: 2019/4/8 0:07
 */
public class HelloServer {
    public static void main(String[] args) throws InterruptedException {
        // 定义一对线程组
        //主线程组，用于接收客户端的连接，但是不做任何处理
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        //从线程组，老板线程组会把任务丢给他，让手下线程去做任务
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        //netty服务器的创建， ServerBootstrap是一个启动类
        try {
            ServerBootstrap serverBootstrap  = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup) //设置主从线程组
                           .channel(NioServerSocketChannel.class)  //设置nio的双向通道
                           .childHandler(new HelloServerInitializer()); //子处理器，用于处理workerGroup

            //用于启动server，并且设置8088为启动的端口号，同时启动的方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();
            //监听关闭的channel，设置位同步方式
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
