/*
    Trần Quang Hiển
    202418893
*/
package Assignment_8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaintenanceService {
    private final List<String> requests = new ArrayList<>();

    public List<String> getRequests() {
        return Collections.unmodifiableList(requests);
    }

    public void handleCriticalAlert(Object sender, DeviceAlertEvent e) {
        if (e.severity() != AlertSeverity.CRITICAL) {
            return;
        }
        requests.add("Maintenance request for " + e.deviceId() + ": " + e.message());
    }
}