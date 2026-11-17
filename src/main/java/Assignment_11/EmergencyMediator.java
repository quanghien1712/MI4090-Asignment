/*
    Trần Quang Hiển
    202418893
*/
package Assignment_11;

import java.util.Objects;

public final class EmergencyMediator implements IEmergencyMediator {
    private final Alarm alarm;
    private final DoorController door;
    private final VentilationController ventilation;

    public EmergencyMediator(Alarm alarm, DoorController door, VentilationController ventilation) {
        this.alarm = Objects.requireNonNull(alarm, "alarm must not be null");
        this.door = Objects.requireNonNull(door, "door must not be null");
        this.ventilation = Objects.requireNonNull(ventilation, "ventilation must not be null");
    }

    @Override
    public void notify(String event) {
        switch (event) {
            case FIRE_DETECTED -> {
                alarm.activate();
                door.unlockAll();
                ventilation.startExhaust();
            }
            case ALL_CLEAR -> {
                alarm.deactivate();
                ventilation.stop();
                door.lockAll();
            }
            default -> System.out.println("[MEDIATOR] Unknown event: " + event);
        }
    }
}