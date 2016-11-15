package netty.in.action.chapter.t06_handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class T_6_WriteHandler extends ChannelHandlerAdapter {

	private ChannelHandlerContext ctx;

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		this.ctx = ctx;
	}

	public void send(String msg) {
		ctx.write(msg);
	}
}