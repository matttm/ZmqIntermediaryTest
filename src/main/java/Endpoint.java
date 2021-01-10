import org.zeromq.ZMQ;

/**
 * Created by Matt Maloney on 1/8/2021
 * matttm : mtm9051
 * matttmaloney@gmail.com
 * mtm9051@rit.edu
 * Language:  Java 1.8
 */
abstract public class Endpoint {
    protected String name;
    protected ZMQ.Socket socket = null;
    protected ZMQ.Context context = null;

    public Endpoint(String name, String protocol, String host, int port) {
        this.name = name;
        context = ZMQ.context(1);
        String url = protocol + "://" + host + ":" + port;
        instantiateSocket(context, url);
    }

    abstract void instantiateSocket(ZMQ.Context context, String url);

    public void sendMessage(String msg) {
        System.out.println(name + " sending: " + msg);
        socket.send(msg.getBytes(ZMQ.CHARSET), 0);
    }

    public String receiveMessage() {
        byte[] reply = socket.recv();
        String s = new String(reply, ZMQ.CHARSET);
        System.out.println(name + " received: " + s);
        return s;
    }

    public void shutdown() {
        System.out.println("Closing socket");
        socket.close();
        context.close();
    }
}
