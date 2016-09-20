package netty.in.action.chapter.t07_decode_encode;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * 编码器，将Integer编码成String，MessageToMessageEncoder实现
 * 
 */
public class T_7_IntegerToStringEncoder extends MessageToMessageEncoder<Integer> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
		out.add(String.valueOf(msg));
	}
}
