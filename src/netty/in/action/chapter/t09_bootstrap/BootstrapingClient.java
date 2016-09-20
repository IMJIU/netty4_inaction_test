package netty.in.action.chapter.t09_bootstrap;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * 引导配置客户端
 * 
 * @author c.k
 * 
 */
public class BootstrapingClient {

	public static void main(String[] args) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class).handler(new SimpleChannelInboundHandler<ByteBuf>() {

			@Override
			protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
				System.out.println("Received data");
				int len = msg.readableBytes();
				byte[] arr = new byte[len];
				msg.getBytes(0, arr);
				System.out.println(msg.hasArray());
				System.out.println(new String(arr));
				msg.clear();
				ctx.writeAndFlush(Unpooled.copiedBuffer("hello I'm client!", CharsetUtil.UTF_8));
			}
			
			@Override
			public void channelActive(ChannelHandlerContext ctx) throws Exception {
				ctx.write(Unpooled.copiedBuffer("active!", CharsetUtil.UTF_8));
				ctx.flush();
			}
		});
		ChannelFuture f = b.connect("localhost", 2048);
		f.addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()) {
					System.out.println("connection finished");
				} else {
					System.out.println("connection failed");
					future.cause().printStackTrace();
				}
			}
			
		});
	}
}
