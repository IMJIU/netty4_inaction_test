package netty.in.action.chapter.t12_spdy;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import org.eclipse.jetty.npn.NextProtoNego;

public class T_a2_SpdyChannelInitializer extends ChannelInitializer<Channel> {

	private final SSLContext context;

	public T_a2_SpdyChannelInitializer(SSLContext context) {
		this.context = context;
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		SSLEngine engine = context.createSSLEngine();
		engine.setUseClientMode(false);
		NextProtoNego.put(engine, new T_a2_DefaultServerProvider());
		NextProtoNego.debug = true;
		pipeline.addLast("sslHandler", new SslHandler(engine));
		pipeline.addLast("chooser", new T_a2_DefaultSpdyOrHttpChooser(1024 * 1024, 1024 * 1024));
	}

}
