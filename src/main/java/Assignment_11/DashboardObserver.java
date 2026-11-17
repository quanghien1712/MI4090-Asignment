/*
    Trần Quang Hiển
    202418893
*/
package Assignment_11;

public final class DashboardObserver implements AlertObserver {
    @Override
    public void update(AlertEvent alertEvent) {
        System.out.printf("[DASHBOARD] %s: %.1f%s [%s]%n", alertEvent.sensorName(),
              alertEvent.value(), alertEvent.unit(), alertEvent.severity());
    }
}