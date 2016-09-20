package netty.in.action.chapter.t08_channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 处理空闲连接和超时是网络应用程序的核心部分。
 * 当发送一条消息后，可以检测连接是否还处于活跃状态，
 * 若很长时间没用了就可以断开连接。
 * Netty提供了很好的解决方案， 有三种不同的ChannelHandler处理闲置和超时连接： 
 * 	•	IdleStateHandler，当一个通道没有进行读写或运行了一段时间后触发IdleStateEvent 
 * 	•	ReadTimeoutHandler，在指定时间内没有接收到任何数据将抛出ReadTimeoutException 
 * 	•	WriteTimeoutHandler，在指定时间内有写入数据将抛出WriteTimeoutException
 *
 */
public class IdleStateHandlerInitializer extends ChannelInitializer<Channel> {

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new IdleStateHandler(0, 0, 60, TimeUnit.SECONDS));
		pipeline.addLast(new HeartbeatHandler());
	}

	public static final class HeartbeatHandler extends ChannelInboundHandlerAdapter {

		private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HEARTBEAT", CharsetUtil.UTF_8));

		@Override
		public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
			if (evt instanceof IdleStateEvent) {
				ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
			} else {
				super.userEventTriggered(ctx, evt);
			}
		}
	}
}
