import org.zeromq.ZMQ;
import org.zeromq.ZMQException;

/**
 * Created by Matt Maloney on 1/8/2021
 * matttm : mtm9051
 * matttmaloney@gmail.com
 * mtm9051@rit.edu
 * Language:  Java 1.8
 */
public class Driver {
    public static void main(String[] argv) {
        try {
            Replier rep = new Replier(
                    "replier", "tcp", "*", 5556);
            Requester req = new Requester(
                    "requester", "tcp", "localhost", 5556);

            req.sendMessage("Msg 1");
            rep.receiveMessage();
            rep.sendMessage("Msg 2");
            req.receiveMessage();
        } catch (ZMQException exc) {
            System.out.println(exc.toString());
        }
    }
}
