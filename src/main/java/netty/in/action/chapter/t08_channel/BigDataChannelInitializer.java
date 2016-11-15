package netty.in.action.chapter.t08_channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;

import java.io.File;
import java.io.FileInputStream;

import javax.net.ssl.SSLContext;

/**
 * 写大量的数据的一个有效的方法是使用异步框架，
 * 如果内存和网络都处于饱满负荷状态，
 * 你需要停止写，否则会报OutOfMemoryError。
 * Netty提供了写文件内容时zero-memory-copy机制，这种方法再将文件内容写到网络堆栈空间时可以获得最大的性能。
 * 使用零拷贝写文件的内容时通过DefaultFileRegion、ChannelHandlerContext、ChannelPipeline，
 * 看下面代码：
 *
 */
public class BigDataChannelInitializer extends ChannelInitializer<Channel> {

	private final SSLContext context;

	private final boolean client;

	private final boolean startTls;

	public BigDataChannelInitializer(SSLContext context, boolean client, boolean startTls) {
		this.context = context;
		this.client = client;
		this.startTls = startTls;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		File file = new File("test.txt");
		FileInputStream fis = new FileInputStream(file);
		FileRegion region = new DefaultFileRegion(fis.getChannel(), 0, file.length());
		Channel channel = ctx.channel();
		channel.writeAndFlush(region).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (!future.isSuccess()) {
					Throwable cause = future.cause();
					// do something
				}
			}
		});
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		// TODO Auto-generated method stub

	}

}
