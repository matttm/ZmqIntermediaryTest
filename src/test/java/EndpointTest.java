import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

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

    @BeforeClass
    public static void setup() {
        String host = "localhost";
        String protocol = "tcp";
        int port = 5556;
        Responder rep = new Responder(
                "replier", protocol, "*", port);
        Requester req = new Requester(
                "requester", protocol, host, port);
    }

    @AfterClass
    public static void shutdown() {
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
