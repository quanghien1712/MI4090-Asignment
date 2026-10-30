/*
    Trần Quang Hiển
    202418893
*/
package Assignment_8;

public class ProjectorRemote extends TeachingDevice {
    private static final int WARNING_BELOW = 20;
    private int batteryLevel;

    public ProjectorRemote(String id, String name, int initialBatteryLevel) {
        super(id, name);
        validatePercent(initialBatteryLevel, "Battery level");
        this.batteryLevel = initialBatteryLevel;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void updateBatteryLevel(int newLevel) {
        validatePercent(newLevel, "Battery level");
        int oldZone = zoneOf(batteryLevel, WARNING_BELOW);
        int newZone = zoneOf(newLevel, WARNING_BELOW);
        batteryLevel = newLevel;

        if (newZone > oldZone) {
            if (newZone == 2) raiseAlert("Projector remote battery is 0%", AlertSeverity.CRITICAL);
            else {
                raiseAlert("Projector remote battery low: " + newLevel + "%", AlertSeverity.WARNING);
            }
        }
    }
}