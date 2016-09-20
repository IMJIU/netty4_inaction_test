package netty.in.action.chapter.t07_decode_encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Integer解码器,ReplayingDecoder实现
 *         当从接收的数据ByteBuf读取integer，若没有足够的字节可读，decode(...)会停止解码，若有足够的字节可读，则会读取数据添加到List列表中。使用ReplayingDecoder或ByteToMessageDecoder是个人喜好的问题，Netty提供了这两种实现，选择哪一个都可以。
        上面讲了byte-to-message的解码实现方式，那message-to-message该如何实现呢？Netty提供了MessageToMessageDecoder抽象类。

 */
public class T_7_ToIntegerReplayingDecoder extends ReplayingDecoder<Void> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		out.add(in.readInt());
	}
}
