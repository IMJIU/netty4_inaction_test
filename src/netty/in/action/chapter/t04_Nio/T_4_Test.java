package netty.in.action.chapter.t04_Nio;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.CharsetUtil;

public class T_4_Test {

	public static void main(String[] args) {
		T_4_Test t = new T_4_Test();
		t.t1_ChannelFutureListener();
		t.t2_thread();
	}

	@Test
	public void t1_ChannelFutureListener() {
		// 创建EmbeddedChannel对象
		EmbeddedChannel channel = new EmbeddedChannel(new ChannelInboundHandlerAdapter());
		// Create ByteBuf that holds data to write
		ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
		// Write data
		ChannelFuture cf = channel.write(buf);
		// Add ChannelFutureListener to get notified after write completes
		cf.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) {
				// Write operation completes without error
				if (future.isSuccess()) {
					System.out.println(".Write successful.");
				} else {
					// Write operation completed but because of error
					System.out.println(".Write error.");
					future.cause().printStackTrace();
				}
			}
		});

	}

	@Test
	public void t2_thread() {
		final Channel channel = new EmbeddedChannel(new ChannelInboundHandlerAdapter(){
			@Override
			public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
				System.out.println(msg);
				super.channelRead(ctx, msg);
			}
			
		});
		// Create ByteBuf that holds data to write
		final ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
		// Create Runnable which writes data to channel
		Runnable writer = new Runnable() {

			@Override
			public void run() {
				channel.write(buf.duplicate());
			}
		};
		// Obtain reference to the Executor which uses threads to execute tasks
		Executor executor = Executors.newCachedThreadPool();
		// write in one thread
		// Hand over write task to executor for execution in thread
		executor.execute(writer);
		// write in another thread
		// Hand over another write task to executor for execution in thread
		executor.execute(writer);

	}
}
