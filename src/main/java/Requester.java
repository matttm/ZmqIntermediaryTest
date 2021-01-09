import org.zeromq.ZMQ;

/**
 * Created by Matt Maloney on 1/8/2021
 * matttm : mtm9051
 * matttmaloney@gmail.com
 * mtm9051@rit.edu
 * Language:  Java 1.8
 */
public class Requester extends Endpoint {

    public Requester(String name, String protocol, String host, int port) {
        super(name, protocol, host, port);
    }

    @Override
    void instantiateSocket(ZMQ.Context context, String url) {
        System.out.println("Connecting to " + url);
        // Socket to talk to clients
        socket = context.socket(ZMQ.REQ);
        socket.connect(url);
    }
}
