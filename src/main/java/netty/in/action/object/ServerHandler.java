package netty.in.action.object;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerHandler extends ChannelInboundHandlerAdapter {

  private static final Logger logger = Logger.getLogger(
      ServerHandler.class.getName());


  @Override
  public void channelRead(
      ChannelHandlerContext ctx, Object msg) throws Exception {
    System.out.println(msg);
    ctx.write(msg);
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
    ctx.close();
  }

  @Override
  public void exceptionCaught(
      ChannelHandlerContext ctx, Throwable cause) throws Exception {
    logger.log(
        Level.WARNING,
        "Unexpected exception from downstream.", cause);
    ctx.close();
  }
}