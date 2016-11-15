package netty.in.action.chapter.t07_decode_encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class T_7_ByteArrayDecoder extends MessageToMessageDecoder<ByteBuf> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		// copy the ByteBuf content to a byte array
		byte[] array = new byte[msg.readableBytes()];
		msg.getBytes(0, array);

		out.add(array);
	}
}
