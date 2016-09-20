package netty.in.action.chapter.t06_handler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class T_6_NotSharableHandler extends ChannelInboundHandlerAdapter {

	private int count;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		count++;
		System.out.println("channelRead(...) called the " + count + " time“");
		ctx.fireChannelRead(msg);
	}

}