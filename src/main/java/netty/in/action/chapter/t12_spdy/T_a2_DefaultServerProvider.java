package netty.in.action.chapter.t12_spdy;


import java.util.Arrays;  
import java.util.Collections;  
import java.util.List;  
  
import org.eclipse.jetty.npn.NextProtoNego.ServerProvider;  
  
public class T_a2_DefaultServerProvider implements ServerProvider {  
  
    private static final List<String> PROTOCOLS = Collections.unmodifiableList(Arrays  
            .asList("spdy/1", "http/1", "http/0", "Unknown"));  
  
    private String protocol;  
  
    public String getSelectedProtocol() {  
        return protocol;  
    }  
  
    @Override  
    public void protocolSelected(String arg0) {  
        this.protocol = arg0;  
    }  
  
    @Override  
    public List<String> protocols() {  
        return PROTOCOLS;  
    }  
  
    @Override  
    public void unsupported() {  
        protocol = "http/1";  
    }  
  
}  
