package netty.in.action.chapter.t12_spdy;

import io.netty.channel.ChannelInboundHandler;
import io.netty.handler.codec.spdy.SpdyOrHttpChooser;

import javax.net.ssl.SSLEngine;

import org.eclipse.jetty.npn.NextProtoNego;

public class T_a2_DefaultSpdyOrHttpChooser extends SpdyOrHttpChooser {

	protected T_a2_DefaultSpdyOrHttpChooser(int maxSpdyContentLength, int maxHttpContentLength) {
		super(maxSpdyContentLength, maxHttpContentLength);
	}

	@Override
	protected SelectedProtocol getProtocol(SSLEngine engine) {
		T_a2_DefaultServerProvider provider = (T_a2_DefaultServerProvider) NextProtoNego.get(engine);
		String protocol = provider.getSelectedProtocol();
		if (protocol == null) {
			return SelectedProtocol.UNKNOWN;
		}
		switch( protocol )
		{
		case "spdy/1":
			return SelectedProtocol.SPDY_3_1;
		case "http/0":
		case "http/1":
			return SelectedProtocol.HTTP_1_1;
		default:
			return SelectedProtocol.UNKNOWN;
		}
	}

	@Override
	protected ChannelInboundHandler createHttpRequestHandlerForHttp() {
		return new T_a2_HttpRequestHandler();
	}

	@Override
	protected ChannelInboundHandler createHttpRequestHandlerForSpdy() {
		return new T_a2_SpdyRequestHandler();
	}

}
