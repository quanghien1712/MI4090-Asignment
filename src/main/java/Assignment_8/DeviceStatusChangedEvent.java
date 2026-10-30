/*
    Trần Quang Hiển
    202418893
*/
package Assignment_8;

import java.time.LocalDateTime;

public record DeviceStatusChangedEvent(
      String deviceId,
      DeviceStatus oldStatus,
      DeviceStatus newStatus,
      LocalDateTime changedAt) {
}