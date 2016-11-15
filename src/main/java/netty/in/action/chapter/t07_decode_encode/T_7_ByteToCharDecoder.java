package netty.in.action.chapter.t07_decode_encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 解码器，将byte转成char
 * 
 */
public class T_7_ByteToCharDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		while (in.readableBytes() >= 2) {
			out.add(Character.valueOf(in.readChar()));
		}
	}

}
