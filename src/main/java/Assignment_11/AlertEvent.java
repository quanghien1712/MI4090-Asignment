/*
    Trần Quang Hiển
    202418893
*/
package Assignment_11;

import java.time.LocalDateTime;
import java.util.Objects;

public record AlertEvent(
      String sensorName,
      double value,
      String unit,
      String severity,
      LocalDateTime occurredAt
) {
    public AlertEvent {
        Objects.requireNonNull(sensorName, "sensorName");
        Objects.requireNonNull(unit, "unit");
        Objects.requireNonNull(severity, "severity");
        Objects.requireNonNull(occurredAt, "occurredAt");
    }
}