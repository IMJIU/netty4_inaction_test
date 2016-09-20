package netty.in.action.chapter.t07_decode_encode;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

@Sharable
public class T_7_ByteArrayEncoder extends MessageToMessageEncoder<byte[]> {

	@Override
	protected void encode(ChannelHandlerContext ctx, byte[] msg, List<Object> out) throws Exception {
		out.add(Unpooled.wrappedBuffer(msg));
	}
}
