package netty.in.action.chapter.t08_channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 添加聚合http消息的Handler
 * 
 * @author c.k
 * 
 */
public class HttpGZipAggregatorInitializer extends ChannelInitializer<Channel> {

	private final boolean client;

	public HttpGZipAggregatorInitializer(boolean client) {
		this.client = client;
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		if (client) {
			pipeline.addLast("codec", new HttpClientCodec());
			// 添加解压缩Handler
			pipeline.addLast("decompressor", new HttpContentDecompressor());
		} else {
			pipeline.addLast("codec", new HttpServerCodec());
			// 添加解压缩Handler
			pipeline.addLast("decompressor", new HttpContentDecompressor());
		}
		pipeline.addLast("aggegator", new HttpObjectAggregator(512 * 1024));
	}
}
