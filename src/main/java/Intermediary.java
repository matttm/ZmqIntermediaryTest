import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Matt Maloney on 1/8/2021
 * matttm : mtm9051
 * matttmaloney@gmail.com
 * mtm9051@rit.edu
 * Language:  Java 1.8
 */

/**
 * The Intermediary class is designed to poll a socket on an interval and
 * pass a message between sockets
 */
public class Intermediary {
    private final AtomicBoolean play$ = new AtomicBoolean(false);
    private Replier inSocket = null;
    private Requester outSocket = null;

    public Intermediary(String protocol, String host1, int port1,
                        String host2, int port2) {
        inSocket = new Replier(
                "Intermediary replier", protocol, "*", port1);
        outSocket = new Requester(
                "Intermediary requester", "tcp", host2, port2);
    }

    Observable<Long> startListening() {
        play$.set(true);
        return Observable.interval(10, TimeUnit.SECONDS)
                .takeWhile(tick -> play$.get())
                .doOnEach(tick -> {
                    pause(); // stop observable
                    String msg = inSocket.receiveMessage();
                    outSocket.sendMessage(msg);
                    msg = outSocket.receiveMessage();
                    inSocket.sendMessage(msg);
                    resume(); // play observable again
                });
    }

    void resume() {
        play$.set(true);
    }

    void pause() {
        play$.set(false);
    }
}
