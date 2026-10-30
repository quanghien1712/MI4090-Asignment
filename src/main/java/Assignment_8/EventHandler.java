/*
    Trần Quang Hiển
    202418893
*/
package Assignment_8;

@FunctionalInterface
public interface EventHandler<TEvent> {
    void handle(Object sender, TEvent e);
}