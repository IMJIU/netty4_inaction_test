package netty.in.action.chapter.t12_spdy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

import javax.net.ssl.SSLContext;

import netty.in.action.spdy.SecureChatSslContextFactory;

public class T_a2_SpdyServer {

	private final NioEventLoopGroup group = new NioEventLoopGroup();

	private final SSLContext context;

	private Channel channel;

	public T_a2_SpdyServer(SSLContext context) {
		this.context = context;
	}

	public ChannelFuture start(InetSocketAddress address) {
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(group).channel(NioServerSocketChannel.class).childHandler(new T_a2_SpdyChannelInitializer(context));
		ChannelFuture future = bootstrap.bind(address);
		future.syncUninterruptibly();
		channel = future.channel();
		return future;
	}

	public void destroy() {
		if (channel != null) {
			channel.close();
		}
		group.shutdownGracefully();
	}

	public static void main(String[] args) {
		SSLContext context = SecureChatSslContextFactory.getServerContext();
		final T_a2_SpdyServer endpoint = new T_a2_SpdyServer(context);
		ChannelFuture future = endpoint.start(new InetSocketAddress(8080));
		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				endpoint.destroy();
			}
		});
		future.channel().closeFuture().syncUninterruptibly();
	}

}
