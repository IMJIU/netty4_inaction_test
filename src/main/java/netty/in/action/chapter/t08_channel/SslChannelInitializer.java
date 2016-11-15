package netty.in.action.chapter.t08_channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
/**
 *  需要注意一点，SslHandler必须要添加到ChannelPipeline的第一个位置，可能有一些例外，但是最好这样来做。
 *  回想一下之前讲解的ChannelHandler，ChannelPipeline就像是一个在处理“入站”数据时先进先出，在处理“出站”数据时后进先出的队列。最先添加的SslHandler会啊在其他Handler处理逻辑数据之前对数据进行加密，从而确保Netty服务端的所有的Handler的变化都是安全的。
        SslHandler提供了一些有用的方法，可以用来修改其行为或得到通知，一旦SSL/TLS完成握手(在握手过程中的两个对等通道互相验证对方，然后选择一个加密密码)，SSL/TLS是自动执行的。看下面方法列表：
•	setHandshakeTimeout(long handshakeTimeout, TimeUnit unit)，设置握手超时时间，ChannelFuture将得到通知
•	setHandshakeTimeoutMillis(long handshakeTimeoutMillis)，设置握手超时时间，ChannelFuture将得到通知
•	getHandshakeTimeoutMillis()，获取握手超时时间值
•	setCloseNotifyTimeout(long closeNotifyTimeout, TimeUnit unit)，设置关闭通知超时时间，若超时，ChannelFuture会关闭失败
•	setHandshakeTimeoutMillis(long handshakeTimeoutMillis)，设置关闭通知超时时间，若超时，ChannelFuture会关闭失败
•	getCloseNotifyTimeoutMillis()，获取关闭通知超时时间
•	handshakeFuture()，返回完成握手后的ChannelFuture
•	close()，发送关闭通知请求关闭和销毁
 *
 */
public class SslChannelInitializer extends ChannelInitializer<Channel> {

	private final SSLContext context;

	private final boolean client;

	private final boolean startTls;

	public SslChannelInitializer(SSLContext context, boolean client, boolean startTls) {
		this.context = context;
		this.client = client;
		this.startTls = startTls;
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		SSLEngine engine = context.createSSLEngine();
		engine.setUseClientMode(client);
		ch.pipeline().addFirst("ssl", new SslHandler(engine, startTls));
	}
}
