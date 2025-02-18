package cn.myzf.netty.client.handler;


import cn.myzf.common.protobuf.Command;
import cn.myzf.common.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogicClientHandler extends SimpleChannelInboundHandler<Message>{
	public Logger log = LoggerFactory.getLogger(this.getClass());

	private final static String CLIENTID = "test";

	// 连接成功后，向server发送消息  
	@Override  
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Message.MessageBase.Builder authMsg = Message.MessageBase.newBuilder();
		authMsg.setClientId(CLIENTID);
		authMsg.setCmd(Command.CommandType.AUTH);
		authMsg.setData("This is auth data");

		ctx.writeAndFlush(authMsg.build());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.debug("连接断开");
	}


	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

	}
}
