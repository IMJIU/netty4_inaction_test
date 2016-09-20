package netty.in.action.chapter.t07_decode_encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Integer解码器,ByteToMessageDecoder实现
 * 
 * @author c.k
 * 
 */
public class T_7_ToIntegerDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() >= 4) {
			out.add(in.readInt());
		}
	}
}
