/*
    Trần Quang Hiển
    202418893
*/
package Assignment_11;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AuditLogObserver implements AlertObserver {
    private final List<String> logs = new ArrayList<>();

    @Override
    public void update(AlertEvent alertEvent) {
        String line = String.format("%s | %s | %.1f%s | %s", alertEvent.occurredAt().toString(),
              alertEvent.sensorName(), alertEvent.value(), alertEvent.unit(), alertEvent.severity());
        logs.add(line);
        System.out.println("[AUDIT] " + line);
    }

    public List<String> getLogs() {
        return Collections.unmodifiableList(logs);
    }
}