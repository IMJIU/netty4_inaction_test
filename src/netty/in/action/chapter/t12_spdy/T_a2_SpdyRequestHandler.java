package netty.in.action.chapter.t12_spdy;

public class T_a2_SpdyRequestHandler extends T_a2_HttpRequestHandler {

	@Override
	protected String getContent() {
		return "This content is transmitted via SPDY\r\n";
	}

}
