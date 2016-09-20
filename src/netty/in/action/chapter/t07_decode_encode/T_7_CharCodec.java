package netty.in.action.chapter.t07_decode_encode;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * 继承CombinedChannelDuplexHandler，用于绑定解码器和编码器
 * 
 * @author c.k
 * 
 */
public class T_7_CharCodec extends CombinedChannelDuplexHandler<T_7_ByteToCharDecoder, T_7_CharToByteEncoder> {

	public T_7_CharCodec() {
		super(new T_7_ByteToCharDecoder(), new T_7_CharToByteEncoder());
	}
}
