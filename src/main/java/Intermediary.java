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

    public Intermediary() {}

    Observable<Long> startListening(String protocol, String host1, int port1,
                                    String host2, int port2) {
        play$.set(true);
        return Observable.interval(10, TimeUnit.MILLISECONDS)
                .takeWhile(tick -> play$.get())
                .doOnEach(tick -> {
                    // pause(); // stop observable
                    if (inSocket == null && outSocket == null) {
                        inSocket = new Replier(
                                "Intermediary replier", protocol, "*", port1);
                        outSocket = new Requester(
                                "Intermediary requester", protocol, host2, port2);
                    }
                    String msg = inSocket.receiveMessage();
                    outSocket.sendMessage(msg);
                    msg = outSocket.receiveMessage();
                    inSocket.sendMessage(msg);
                    // resume(); // play observable again
                })
                .doOnDispose(() -> {
                    inSocket.shutdown();
                    outSocket.shutdown();
                });
    }

    void resume() {
        play$.set(true);
    }

    void pause() {
        play$.set(false);
    }
}
