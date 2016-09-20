package netty.in.action.chapter.t09_bootstrap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * 引导服务器配置
 * 
 * @author c.k
 * 
 */
public class BootstrapingServer {

	public static void main(String[] args) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new SimpleChannelInboundHandler<ByteBuf>() {

			@Override
			protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
				System.out.println("Received data");
				int len = msg.readableBytes();
				byte[] arr = new byte[len];
				msg.getBytes(0, arr);
				System.out.println(msg.hasArray());
				System.out.println(new String(arr));
				msg.clear();
				ctx.writeAndFlush(Unpooled.copiedBuffer("hello I'm server!", CharsetUtil.UTF_8));
			}
			@Override
			public void channelActive(ChannelHandlerContext ctx) throws Exception {
				ctx.writeAndFlush(Unpooled.copiedBuffer("active!", CharsetUtil.UTF_8));
			}
		});
		ChannelFuture f = b.bind(2048);
		f.addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()) {
					System.out.println("Server bound");
				} else {
					System.err.println("bound fail");
					future.cause().printStackTrace();
				}
			}
		});
	}
}
