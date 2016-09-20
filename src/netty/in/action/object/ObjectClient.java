package netty.in.action.object;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class ObjectClient {

	private String host;

	private int port;

	private int messageSize;

	public ObjectClient(String host, int port, int messageSize) {
		this.host = host;
		this.port = port;
		this.messageSize = messageSize;
	}

	public void run() throws InterruptedException {
		Bootstrap bootstrap = new Bootstrap();
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

		try {
			bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new ObjectEncoder(), new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)), new ClientHandler(messageSize));
				}
			});

			ChannelFuture future = bootstrap.connect(host, port).sync();

			future.channel().closeFuture().sync();
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		final String host = "127.0.0.1";
		final int port = 8080;
		final int messageSize = 200;

		new ObjectClient(host, port, messageSize).run();
	}
}