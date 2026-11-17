/*
    Trần Quang Hiển
    202418893
*/
package Assignment_11;

import java.util.Objects;

public final class EmergencyBridgeObserver implements AlertObserver {
    private final IEmergencyMediator mediator;

    public EmergencyBridgeObserver(IEmergencyMediator mediator) {
        this.mediator = Objects.requireNonNull(mediator, "mediator must not be null");
    }

    @Override
    public void update(AlertEvent alertEvent) {
        if ("CRITICAL".equals(alertEvent.severity())) {
            System.out.println("[BRIDGE] CRITICAL from " + alertEvent.sensorName() + " -> FIRE_DETECTED");
            mediator.notify(IEmergencyMediator.FIRE_DETECTED);
        }
    }
}