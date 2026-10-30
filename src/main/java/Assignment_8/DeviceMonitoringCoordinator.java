/*
    Trần Quang Hiển
    202418893
*/
package Assignment_8;

import java.util.List;

public class DeviceMonitoringCoordinator implements AutoCloseable {
    private final List<TeachingDevice> devices;
    private boolean subscribed;

    private final EventHandler<DeviceAlertEvent> notifyHandler;
    private final EventHandler<DeviceAlertEvent> maintenanceHandler;
    private final EventHandler<DeviceStatusChangedEvent> auditHandler;

    public DeviceMonitoringCoordinator(List<TeachingDevice> devices,
                                       ConsoleNotificationService notificationService,
                                       DeviceAuditLogger auditLogger,
                                       MaintenanceService maintenanceService) {
        this.devices = List.copyOf(devices);
        this.notifyHandler = notificationService::handleAlert;
        this.maintenanceHandler = maintenanceService::handleCriticalAlert;
        this.auditHandler = auditLogger::handleStatusChanged;
    }

    public void subscribe() {
        if (subscribed) return;
        for (TeachingDevice d : devices) {
            d.alertRaised().subscribe(notifyHandler);
            d.alertRaised().subscribe(maintenanceHandler);
            d.statusChanged().subscribe(auditHandler);
        }
        subscribed = true;
    }

    public void unsubscribe() {
        if (!subscribed) return;
        for (TeachingDevice d : devices) {
            d.alertRaised().unsubscribe(notifyHandler);
            d.alertRaised().unsubscribe(maintenanceHandler);
            d.statusChanged().unsubscribe(auditHandler);
        }
        subscribed = false;
    }

    @Override
    public void close() {
        unsubscribe();
    }
}