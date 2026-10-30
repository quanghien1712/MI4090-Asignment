/*
    Trần Quang Hiển
    202418893
*/
package Assignment_8;

import java.time.LocalDateTime;

public abstract class TeachingDevice {
    private final String id;
    private final String name;
    private DeviceStatus status;

    private final EventSource<DeviceStatusChangedEvent> statusChanged = new EventSource<>();
    private final EventSource<DeviceAlertEvent> alertRaised = new EventSource<>();

    protected TeachingDevice(String id, String name) {
        this.id = id;
        this.name = name;
        this.status = DeviceStatus.AVAILABLE;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public DeviceStatus getStatus() {
        return status;
    }

    public Event<DeviceStatusChangedEvent> statusChanged() {
        return statusChanged;
    }
    public Event<DeviceAlertEvent> alertRaised() {
        return alertRaised;
    }

    public void changeStatus(DeviceStatus newStatus) {
        if (newStatus == status) {
            return;
        }
        DeviceStatus oldStatus = status;
        status = newStatus;
        onStatusChanged(new DeviceStatusChangedEvent(id, oldStatus, newStatus, LocalDateTime.now()));
    }

    protected void raiseAlert(String message, AlertSeverity severity) {
        onAlertRaised(new DeviceAlertEvent(id, message, severity, LocalDateTime.now()));
    }

    protected void onStatusChanged(DeviceStatusChangedEvent e) {
        statusChanged.publish(this, e);
    }

    protected void onAlertRaised(DeviceAlertEvent e) {
        alertRaised.publish(this, e);
    }

    protected void validatePercent(int value, String what) {
        if (value < 0 || value > 100) {
            throw new InvalidDeviceReadingException(id, value,
                  what + " must be between 0 and 100, but was " + value);
        }
    }

    // zone: 0-normal, 1-warning, 2-critical
    protected static int zoneOf(int value, int warningBelow) {
        if (value == 0) return 2;
        if (value < warningBelow) return 1;
        return 0;
    }
}