package netty.in.action.chapter.t06_handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * 实现ChannelInboundHandlerAdapter的Handler，不会自动释放接收的消息对象
 * 
 */
public class T_6_DiscardHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 手动释放消息
		ReferenceCountUtil.release(msg);
	}
}
