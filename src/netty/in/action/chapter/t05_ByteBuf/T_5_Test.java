package netty.in.action.chapter.t05_ByteBuf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Random;

import org.junit.Test;

public class T_5_Test {
	
	public static void main(String[] args) {
		T_5_Test t = new T_5_Test();
		t.t1_directBuffer();
//		t.t2_CompositeByteBuf();
//		t.t3_read();
//		t.t4_read_write();
//		t.t5_clice();
//		t.t6_Allocator();
//		t.t7_Unpooled();
//		t.t8_ByteBufUtil();
    }

	@Test
	public void t1_directBuffer() {
		ByteBuf directBuf = Unpooled.directBuffer(16);
		directBuf.writeBytes("12321".getBytes());
		if (!directBuf.hasArray()) {
			int len = directBuf.readableBytes();
			byte[] arr = new byte[len];
			directBuf.getBytes(0, arr);
			System.out.println(directBuf.hasArray());
			System.out.println(new String(arr));
		}
		directBuf = Unpooled.buffer(16);
		directBuf.writeBytes("12321".getBytes());
		if (directBuf.hasArray()) {
			int len = directBuf.readableBytes();
			byte[] arr = new byte[len];
			directBuf.getBytes(0, arr);
			System.out.println(directBuf.hasArray());
			System.out.println(new String(arr));
		}
	}

	@Test
	public void t2_CompositeByteBuf() {
		CompositeByteBuf compBuf = Unpooled.compositeBuffer();
		ByteBuf heapBuf = Unpooled.buffer(8);
		ByteBuf directBuf = Unpooled.directBuffer(16);
		// 添加ByteBuf到CompositeByteBuf
		compBuf.addComponents(heapBuf, directBuf);
		// 删除第一个ByteBuf
		compBuf.removeComponent(0);
		Iterator<ByteBuf> iter = compBuf.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next().toString());
		}
		// 使用数组访问数据
		if (!compBuf.hasArray()) {
			int len = compBuf.readableBytes();
			byte[] arr = new byte[len];
			compBuf.getBytes(0, arr);
			System.out.println("==================");
			System.out.println(new String(arr));
		}

	}

	@Test
	public void t3_read() {
		// create a ByteBuf of capacity is 16
		ByteBuf buf = Unpooled.buffer(16);
		// write data to buf
		for (int i = 0; i < 16; i++) {
			buf.writeByte(i + 1);
		}
		// read data from buf
		for (int i = 0; i < buf.capacity(); i++) {
			System.out.println(buf.getByte(i));
		}
	}

	@Test
	public void t4_read_write() {
		Random random = new Random();
		ByteBuf buf = Unpooled.buffer(16);
		while (buf.writableBytes() >= 4) {
			buf.writeInt(random.nextInt());
		}

		while (buf.isReadable()) {
			System.out.println(buf.readByte());
		}

	}

	@Test
	public void t5_clice() {
		// get a Charset of UTF-8
		Charset utf8 = Charset.forName("UTF-8");
		// get a ByteBuf
		ByteBuf buf = Unpooled.copiedBuffer("“Netty in Action rocks!“", utf8);
		// slice
		ByteBuf sliced = buf.slice(0, 14);
		// copy
		ByteBuf copy = buf.copy(0, 14);
		// print "“Netty in Action rocks!“"
		System.out.println(buf.toString(utf8));
		// print "?Netty in Act"
		System.out.println(sliced.toString(utf8));
		// print "?Netty in Act"
		System.out.println(copy.toString(utf8));
	}

	/**
	 * Netty有两种不同的ByteBufAllocator实现， 
	 * 一个实现ByteBuf实例池将分配和回收成本以及内存使用降到最低；
	 * 另一种实现是每次使用都创建一个新的ByteBuf实例。
	 * Netty默认使用PooledByteBufAllocator，
	 * 我们可以通过ChannelConfig或通过引导设置一个不同的实现来改变
	 */
	@Test
	public void t6_Allocator() {
		final ByteBuf buf = Unpooled.buffer(16);
		EventLoopGroup group = new NioEventLoopGroup();
		int port = 1024;
		ServerBootstrap b = new ServerBootstrap();

		b.group(group).channel(NioServerSocketChannel.class)
		.localAddress(new InetSocketAddress(port))
		.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				// get ByteBufAllocator instance by Channel.alloc()
				ByteBufAllocator alloc0 = ch.alloc();
				alloc0.buffer();
				alloc0.directBuffer();
				alloc0.compositeBuffer();

				ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {

					@Override
					public void channelActive(ChannelHandlerContext ctx) throws Exception {
						// get ByteBufAllocator instance by
						// ChannelHandlerContext.alloc()
						ByteBufAllocator alloc1 = ctx.alloc();
						ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
						
						alloc1.buffer();
						alloc1.directBuffer();
						alloc1.compositeBuffer();
					}
				});
			}
		});

	}

	@Test
	public void t7_Unpooled() {
		// 创建复合缓冲区
		CompositeByteBuf compBuf = Unpooled.compositeBuffer();
		// 创建堆缓冲区
		ByteBuf heapBuf = Unpooled.buffer(8);
		// 创建直接缓冲区
		ByteBuf directBuf = Unpooled.directBuffer(16);

	}

	@Test
	public void t8_ByteBufUtil() {
		ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
		String s = ByteBufUtil.hexDump(buf);
		System.out.println(s);
	}

}
