import io.reactivex.disposables.Disposable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Matt Maloney on 1/14/2021
 * matttm : mtm9051
 * matttmaloney@gmail.com
 * mtm9051@rit.edu
 * Language:  Java 1.8
 */
public class IntermediaryTest {
    private Requester requester = null;
    private Responder responder = null;
    private Intermediary intermediary = null;
    private Disposable ref = null;

    @Before
    public void setup() {
        String host = "localhost";
        String protocol = "tcp";
        int port1 = 5556;
        int port2 = 5557;
        responder = new Responder(
                "replier", protocol, "*", port2);
        requester = new Requester(
                "requester", protocol, host, port1);
        intermediary = new Intermediary();
        ref = intermediary.startListening(protocol, host,
                port1, host, port2).subscribe();
    }

    @After
    public void shutdown() {
        responder.shutdown();
        requester.shutdown();
        ref.dispose();
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
