import org.junit.*;

/**
 * Created by Matt Maloney on 1/13/2021
 * matttm : mtm9051
 * matttmaloney@gmail.com
 * mtm9051@rit.edu
 * Language:  Java 1.8
 */
public class EndpointTest {
    private Requester requester = null;
    private Responder responder = null;

    @Before
    public void setup() {
        String host = "localhost";
        String protocol = "tcp";
        int port = 5556;
        responder = new Responder(
                "replier", protocol, "*", port);
        requester = new Requester(
                "requester", protocol, host, port);
    }

    @After
    public void shutdown() {
        responder.shutdown();
        requester.shutdown();
    }

    @Test
    public void requestReply() {
        String msg;
        String req = "request";
        String res = "response";
        requester.sendMessage(req);
        msg = responder.receiveMessage();
        assert msg.equals(req);
        responder.sendMessage(res);
        msg = requester.receiveMessage();
        assert msg.equals(res);
    }
}
