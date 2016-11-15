package netty.in.action.chapter.t08_channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * 如果你需要在ChannelPipeline中有一个解码器和编码器，
 * 还分别有一个在客户端和服务器简单的编解码器：HttpClientCodec和HttpServerCodec。
 * 在ChannelPipelien中有解码器和编码器(或编解码器)后就可以操作不同的HttpObject消息了；
 * 但是HTTP请求和响应可以有很多消息数据，你需要处理不同的部分，可能也需要聚合这些消息数据， 这是很麻烦的。
 * 为了解决这个问题，Netty提供了一个聚合器，
 * 它将消息部分合并到FullHttpRequest和FullHttpResponse，因此不需要担心接收碎片消息数据。
 *
 */
public class HttpDecoderEncoderInitializer extends ChannelInitializer<Channel> {

	private final boolean client;

	public HttpDecoderEncoderInitializer(boolean client) {
		this.client = client;
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		if (client) {
			pipeline.addLast("decoder", new HttpResponseDecoder());
			pipeline.addLast("", new HttpRequestEncoder());
		} else {
			pipeline.addLast("decoder", new HttpRequestDecoder());
			pipeline.addLast("encoder", new HttpResponseEncoder());
		}
	}
}
