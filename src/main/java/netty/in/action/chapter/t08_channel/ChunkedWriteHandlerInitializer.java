package netty.in.action.chapter.t08_channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;
import java.io.FileInputStream;

/**
 * 如果只想发送文件中指定的数据块应该怎么做呢？
 * Netty提供了ChunkedWriteHandler，允许通过处理ChunkedInput来写大的数据块。
 * 下面是ChunkedInput的一些实现类： 
 * • ChunkedFile 
 * • ChunkedNioFile 
 * • ChunkedStream 
 * • ChunkedNioStream
 *
 */
public class ChunkedWriteHandlerInitializer extends ChannelInitializer<Channel> {

	private final File file;

	public ChunkedWriteHandlerInitializer(File file) {
		this.file = file;
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ch.pipeline().addLast(new ChunkedWriteHandler()).addLast(new WriteStreamHandler());
	}

	public final class WriteStreamHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			super.channelActive(ctx);
			ctx.writeAndFlush(new ChunkedStream(new FileInputStream(file)));
		}
	}
}
