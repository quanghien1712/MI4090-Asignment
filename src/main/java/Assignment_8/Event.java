/*
    Trần Quang Hiển
    202418893
*/
package Assignment_8;

public interface Event<TEvent> {
    void subscribe(EventHandler<TEvent> handler);
    void unsubscribe(EventHandler<TEvent> handler);
}