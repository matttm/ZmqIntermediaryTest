import io.reactivex.disposables.Disposable;
import org.zeromq.ZMQException;

/**
 * Created by Matt Maloney on 1/8/2021
 * matttm : mtm9051
 * matttmaloney@gmail.com
 * mtm9051@rit.edu
 * Language:  Java 1.8
 */

/***
 * This Driver is to facilitate a test running of the Intermediary class, which
 * will pass the message from the requester to the replier
 */
public class Driver {
    public static void main(String[] argv) {
        String protocol = "tcp";
        String host = "localhost";
        int port1 = 5556;
        int port2 = 5557;
        try {
            Replier rep = new Replier(
                    "replier", protocol, "*", port2);

            Intermediary mediator = new Intermediary(protocol, host,
                    port1, host, port2);

            Requester req = new Requester(
                    "requester", protocol, host, port1);

            Disposable ref = mediator.startListening().subscribe();

            req.sendMessage("Msg 1");
            rep.receiveMessage();

            rep.sendMessage("Msg 2");
            req.receiveMessage();

            rep.sendMessage("Msg 3");
            req.receiveMessage();

            ref.dispose();

        } catch (ZMQException exc) {
            System.out.println(exc.toString());
        }
    }
}
