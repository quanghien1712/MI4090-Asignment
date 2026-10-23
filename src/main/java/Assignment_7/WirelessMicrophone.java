/*
    Trần Quang Hiển
    202418893
 */

package Assignment_7;

public class WirelessMicrophone extends TeachingDevice {
    private static final int MIN_BATTERY_TO_USE = 30;
    private static final int MIN_SIGNAL_TO_USE = 40;

    private final int batteryLevel;
    private final int signalQuality;

    public WirelessMicrophone(String id, String name, String storageLocation,
                              int batteryLevel, int signalQuality) {
        super(id, name, storageLocation);
        if (batteryLevel < 0 || batteryLevel > 100)
            throw new IllegalArgumentException("Muc pin phai tu 0 den 100");
        if (signalQuality < 0 || signalQuality > 100)
            throw new IllegalArgumentException("Chat luong tin hieu phai tu 0 den 100");
        this.batteryLevel = batteryLevel;
        this.signalQuality = signalQuality;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public int getSignalQuality() {
        return signalQuality;
    }

    @Override
    public boolean isReadyForUse() {
        return getStatus() == DeviceStatus.AVAILABLE
              && batteryLevel >= MIN_BATTERY_TO_USE
              && signalQuality >= MIN_SIGNAL_TO_USE;
    }

    @Override
    public double getDefaultPriorityScore() {
        return (batteryLevel + signalQuality) / 2.0;
    }
}