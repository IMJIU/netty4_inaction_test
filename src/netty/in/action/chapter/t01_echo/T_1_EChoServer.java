package netty.in.action.chapter.t01_echo;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class T_1_EChoServer {

	private final int port;
	private final String host;

	public T_1_EChoServer(String host,int port) {
		this.port = port;
		this.host = host;
	}

	public void start() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			// create ServerBootstrap instance
			ServerBootstrap b = new ServerBootstrap();
			// Specifies NIO transport, local socket address
			// Adds handler to channel pipeline
			b.group(group)
			.channel(NioServerSocketChannel.class)
			.localAddress(new InetSocketAddress(host, port))
			.childHandler(new ChannelInitializer<Channel>() {
				@Override
				protected void initChannel(Channel ch) throws Exception {
					ch.pipeline().addLast(new T_1_EchoServerHandler());
				}
			});
			// Binds server, waits for server to close, and releases resources
			ChannelFuture f = b.bind().sync();
			System.out.println(T_1_EChoServer.class.getName() + "started and listen on “" + f.channel().localAddress());
			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}
	}

	public static void main(String[] args) throws Exception {
		new T_1_EChoServer("localhost",8888).start();
	}

}
