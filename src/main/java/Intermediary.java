import org.zeromq.ZMQ;

/**
 * Created by Matt Maloney on 1/8/2021
 * matttm : mtm9051
 * matttmaloney@gmail.com
 * mtm9051@rit.edu
 * Language:  Java 1.8
 */
public class Intermediary {
    private Replier inSocket = null;
    private Requester outSocket = null;

    public Intermediary(String protocol, String host1, int port1,
                        String host2, int port2) {
        inSocket = new Replier(
                "Intermediary replier", protocol, "*", port1);
        outSocket = new Requester(
                "Intermediary requester", "tcp", host2, port2);
    }

    void forwardRequest() {
        String msg = inSocket.receiveMessage();
        outSocket.sendMessage(msg);
    }
    void forwardReply() {
        String msg = outSocket.receiveMessage();
        inSocket.sendMessage(msg);
    }
}
