package netty.in.action.chapter.t06_handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.CharsetUtil;

import org.junit.Test;

public class T_6_Test {

	@Test
	public void t1() {
		Channel ch = new EmbeddedChannel(new ChannelInboundHandlerAdapter());
		ChannelPipeline pipeline = ch.pipeline();
		ChannelHandler handler1 = new ChannelInboundHandlerAdapter();
		pipeline.addLast("handler1", handler1);
		pipeline.addFirst("handler2", new ChannelInboundHandlerAdapter());
		pipeline.addLast("handler3", new ChannelInboundHandlerAdapter());
		pipeline.remove("handler3");
		pipeline.remove(handler1);
		pipeline.replace("handler2", "handler4", new ChannelInboundHandlerAdapter());
	}

	public void t2_initChannel(SocketChannel ch) {
		ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {

			@Override
			public void channelActive(ChannelHandlerContext ctx) throws Exception {
				// Event via Channel
				Channel channel = ctx.channel();
				channel.write(Unpooled.copiedBuffer("netty in action", CharsetUtil.UTF_8));
				// Event via ChannelPipeline
				ChannelPipeline pipeline = ctx.pipeline();
				pipeline.write(Unpooled.copiedBuffer("netty in action", CharsetUtil.UTF_8));
			}
		});
	}

	@Test
	public void t3_跳过某个handler() {
		// Get reference of ChannelHandlerContext
		ChannelHandlerContext ctx = null;
		// Write buffer via ChannelHandlerContext
		ctx.write(Unpooled.copiedBuffer("Netty in Action", CharsetUtil.UTF_8));
	}

	

	

}
