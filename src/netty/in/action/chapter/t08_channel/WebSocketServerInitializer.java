package netty.in.action.chapter.t08_channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * WebSocket Server，若想使用SSL加密，将SslHandler加载ChannelPipeline的最前面即可
 * 下面列表列出了Netty中WebSocket类型：
•	BinaryWebSocketFrame，包含二进制数据
•	TextWebSocketFrame，包含文本数据
•	ContinuationWebSocketFrame，包含二进制数据或文本数据，BinaryWebSocketFrame和TextWebSocketFrame的结合体

•	CloseWebSocketFrame，WebSocketFrame代表一个关闭请求，包含关闭状态码和短语
•	PingWebSocketFrame，WebSocketFrame要求PongWebSocketFrame发送数据
•	PongWebSocketFrame，WebSocketFrame要求PingWebSocketFrame响应
 */
public class WebSocketServerInitializer extends ChannelInitializer<Channel> {

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ch.pipeline().addLast(new HttpServerCodec(), new HttpObjectAggregator(65536), new WebSocketServerProtocolHandler("/websocket"), new TextFrameHandler(),
		        new BinaryFrameHandler(), new ContinuationFrameHandler());
	}

	public static final class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
			// handler text frame
		}
	}

	public static final class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
			// handler binary frame
		}
	}

	public static final class ContinuationFrameHandler extends SimpleChannelInboundHandler<ContinuationWebSocketFrame> {

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, ContinuationWebSocketFrame msg) throws Exception {
			// handler continuation frame
		}
	}
}
