package netty.in.action.chapter.t07_decode_encode;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * 将接收的Integer消息转成String类型，MessageToMessageDecoder实现
 * 
 */
public class T_7_IntegerToStringDecoder extends MessageToMessageDecoder<Integer> {

	@Override
	protected void decode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
		out.add(String.valueOf(msg));
	}
}
