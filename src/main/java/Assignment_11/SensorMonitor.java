/*
    Trần Quang Hiển
    202418893
*/
package Assignment_11;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class SensorMonitor {
    public static final double WARNING_THRESHOLD = 50;
    public static final double CRITICAL_THRESHOLD = 70;

    private final List<AlertObserver> observers = new ArrayList<>();

    public void attach(AlertObserver observer) {
        Objects.requireNonNull(observer, "observer must not be null");
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void detach(AlertObserver observer) {
        observers.remove(observer);
    }

    public int observerCount() {
        return observers.size();
    }

    public void checkSensor(String sensorName, Sensor sensor) {
        Objects.requireNonNull(sensorName, "sensorName must not be null");
        Objects.requireNonNull(sensor, "sensor must not be null");
        double value = sensor.readValue();
        String severity = classify(value);
        if (severity == null) {
            return;
        }
        AlertEvent alertEvent = new AlertEvent(sensorName, value, sensor.getUnit(), severity, LocalDateTime.now());
        notifyObservers(alertEvent);
    }

    private String classify(double value) {
        if (value >= CRITICAL_THRESHOLD) {
            return "CRITICAL";
        }
        if (value >= WARNING_THRESHOLD) {
            return "WARNING";
        }
        return null;
    }

    private void notifyObservers(AlertEvent alertEvent) {
        List<AlertObserver> snapshot = List.copyOf(observers);
        for (AlertObserver observer : snapshot) {
            try {
                observer.update(alertEvent);
            } catch (RuntimeException ex) {
                System.out.println("[MONITOR-ERROR] " + observer.getClass().getSimpleName() + " failed: " + ex.getMessage());
            }
        }
    }
}