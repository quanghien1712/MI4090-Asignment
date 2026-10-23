/*
    Trần Quang Hiển
    202418893
 */

package Assignment_7;

public class ProjectorRemote extends TeachingDevice {
    private static final int MIN_BATTERY_TO_USE = 20;

    private final int batteryLevel;

    public ProjectorRemote(String id, String name, String storageLocation, int batteryLevel) {
        super(id, name, storageLocation);
        if (batteryLevel < 0 || batteryLevel > 100)
            throw new IllegalArgumentException("Muc pin phai tu 0 den 100");
        this.batteryLevel = batteryLevel;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    @Override
    public boolean isReadyForUse() {
        return getStatus() == DeviceStatus.AVAILABLE && batteryLevel >= MIN_BATTERY_TO_USE;
    }

    @Override
    public double getDefaultPriorityScore() {
        return batteryLevel;
    }
}