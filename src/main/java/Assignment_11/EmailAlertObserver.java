/*
    Trần Quang Hiển
    202418893
*/
package Assignment_11;

import java.util.Objects;

public final class EmailAlertObserver implements AlertObserver {
    private final String recipient;

    public EmailAlertObserver(String recipient) {
        this.recipient = Objects.requireNonNull(recipient, "recipient must not be null");
    }

    @Override
    public void update(AlertEvent alertEvent) {
        System.out.printf("[EMAIL] To: %s | Subject: [%s] %s at %.1f%s%n", recipient, alertEvent.severity(),
              alertEvent.sensorName(), alertEvent.value(), alertEvent.unit());
    }
}