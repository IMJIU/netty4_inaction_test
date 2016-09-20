package netty.in.action.chapter.t07_decode_encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器，将Integer值编码成byte[]，MessageToByteEncoder实现
 * 
 */
public class T_7_IntegerToByteEncoder extends MessageToByteEncoder<Integer> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Integer msg, ByteBuf out) throws Exception {
		out.writeInt(msg);
	}
}
