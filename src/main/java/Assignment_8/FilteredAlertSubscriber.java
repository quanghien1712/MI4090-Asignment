/*
    Trần Quang Hiển
    202418893
*/
package Assignment_8;

import java.util.Objects;
import java.util.function.Consumer;

public class FilteredAlertSubscriber {
    private final AlertFilter filter;
    private final Consumer<DeviceAlertEvent> action;

    public FilteredAlertSubscriber(AlertFilter filter, Consumer<DeviceAlertEvent> action) {
        this.filter = Objects.requireNonNull(filter);
        this.action = Objects.requireNonNull(action);
    }

    public void handleAlert(Object sender, DeviceAlertEvent e) {
        if (filter.test(e)) {
            action.accept(e);
        }
    }
}