package netty.in.action.chapter.t06_handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 继承SimpleChannelInboundHandler，会自动释放消息对象
 * 
 * 
 */
public class T_6_SimpleDiscardHandler extends SimpleChannelInboundHandler<Object> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 不需要手动释放
	}
}
