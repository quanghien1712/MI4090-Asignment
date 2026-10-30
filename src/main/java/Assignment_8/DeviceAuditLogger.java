/*
    Trần Quang Hiển
    202418893
*/
package Assignment_8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeviceAuditLogger {
    private final List<String> entries = new ArrayList<>();

    public List<String> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public void handleStatusChanged(Object sender, DeviceStatusChangedEvent e) {
        entries.add(e.changedAt() + ": " + e.deviceId() + ", "
              + e.oldStatus() + " -> " + e.newStatus());
    }
}