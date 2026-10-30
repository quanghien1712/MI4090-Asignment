/*
    Trần Quang Hiển
    202418893
*/
package Assignment_8;

public class WirelessMicrophone extends TeachingDevice {
    private static final int BATTERY_WARNING_BELOW = 30;
    private static final int SIGNAL_WARNING_BELOW  = 40;

    private int batteryLevel;
    private int signalQuality;

    public WirelessMicrophone(String id, String name, int initialBattery, int initialSignal) {
        super(id, name);
        validatePercent(initialBattery, "Battery level");
        validatePercent(initialSignal, "Signal quality");
        this.batteryLevel = initialBattery;
        this.signalQuality = initialSignal;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }
    public int getSignalQuality() {
        return signalQuality;
    }

    public void updateBatteryLevel(int newLevel) {
        validatePercent(newLevel, "Battery level");
        int oldZone = zoneOf(batteryLevel, BATTERY_WARNING_BELOW);
        int newZone = zoneOf(newLevel, BATTERY_WARNING_BELOW);
        batteryLevel = newLevel;
        if (newZone > oldZone) {
            if (newZone == 2) raiseAlert("Microphone battery is 0%", AlertSeverity.CRITICAL);
            else {
                raiseAlert("Microphone battery low: " + newLevel + "%", AlertSeverity.WARNING);
            }
        }
    }

    public void updateSignalQuality(int newQuality) {
        validatePercent(newQuality, "Signal quality");
        int oldZone = zoneOf(signalQuality, SIGNAL_WARNING_BELOW);
        int newZone = zoneOf(newQuality, SIGNAL_WARNING_BELOW);
        signalQuality = newQuality;
        if (newZone > oldZone) {
            if (newZone == 2) raiseAlert("Microphone signal lost", AlertSeverity.CRITICAL);
            else {
                raiseAlert("Microphone signal poor: " + newQuality + "%", AlertSeverity.WARNING);
            }
        }
    }
}