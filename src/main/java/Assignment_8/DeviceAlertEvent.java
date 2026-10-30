/*
    Trần Quang Hiển
    202418893
*/
package Assignment_8;

import java.time.LocalDateTime;

public record DeviceAlertEvent(
      String deviceId,
      String message,
      AlertSeverity severity,
      LocalDateTime occurredAt) {
}