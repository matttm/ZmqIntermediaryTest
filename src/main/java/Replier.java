import org.zeromq.ZMQ;


/**
 * Created by Matt Maloney on 1/8/2021
 * matttm : mtm9051
 * matttmaloney@gmail.com
 * mtm9051@rit.edu
 * Language:  Java 1.8
 */
public class Replier extends Endpoint {
    public Replier(String name, String protocol, String host, int port) {
        super(name, protocol, host, port);
    }

    @Override
    void instantiateSocket(ZMQ.Context context, String url) {
        System.out.println("Binding to " + url);
        // Socket to talk to clients
        socket = context.socket(ZMQ.REP);
        socket.bind(url);
    }
}
