/*
    Trần Quang Hiển
    202418893
 */

package Assignment_7.exception;

import Assignment_7.DeviceStatus;

public class DeviceNotReadyException extends Exception {
    private final String deviceId;
    private final DeviceStatus currentStatus;

    public DeviceNotReadyException(String deviceId, DeviceStatus currentStatus) {
        super("Thiet bi " + deviceId + " chua available, trang thai: " + currentStatus);
        this.deviceId = deviceId;
        this.currentStatus = currentStatus;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public DeviceStatus getCurrentStatus() {
        return currentStatus;
    }
}