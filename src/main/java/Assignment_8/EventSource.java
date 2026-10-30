/*
    Trần Quang Hiển
    202418893
*/
package Assignment_8;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventSource<TEvent> implements Event<TEvent> {
    private final List<EventHandler<TEvent>> handlers = new CopyOnWriteArrayList<>();

    @Override
    public void subscribe(EventHandler<TEvent> handler) {
        if (handler == null) {
            throw new IllegalArgumentException("handler khong duoc null");
        }
        if (!handlers.contains(handler)) {
            handlers.add(handler);
        }
    }

    @Override
    public void unsubscribe(EventHandler<TEvent> handler) {
        handlers.remove(handler);
    }

    public void publish(Object sender, TEvent e) {
        for (EventHandler<TEvent> h : handlers) {
            try {
                h.handle(sender, e);
            } catch (RuntimeException exception) {
                System.err.println("[EventSource] Handler failed: " + exception);
            }
        }
    }
}