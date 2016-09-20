package netty.in.action.chapter.t07_decode_encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器，将char转成byte
 */
public class T_7_CharToByteEncoder extends MessageToByteEncoder<Character> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Character msg, ByteBuf out) throws Exception {
		out.writeChar(msg);
	}
}
