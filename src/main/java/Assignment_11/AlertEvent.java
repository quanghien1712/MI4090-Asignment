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
        Objects.requireNonNull(sensorName, "sensorName must not be null");
        Objects.requireNonNull(unit, "unit must not be null");
        Objects.requireNonNull(severity, "severity must not be null");
        Objects.requireNonNull(occurredAt, "occurredAt must not be null");
    }
}