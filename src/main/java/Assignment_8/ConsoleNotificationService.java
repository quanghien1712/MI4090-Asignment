/*
    Trần Quang Hiển
    202418893
*/
package Assignment_8;

public class ConsoleNotificationService {
    public void handleAlert(Object sender, DeviceAlertEvent e) {
        System.out.printf("[%s] %s: %s%n", e.severity(), e.deviceId(), e.message());
    }
}