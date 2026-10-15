/*
    Trần Quang Hiển
    202418893
*/

package Assignment_6.exception;

public class DeviceNotInSlipException extends BorrowingException {
    private final String deviceId;

    public DeviceNotInSlipException(String deviceId) {
        super("Thiet bi " + deviceId + " khong thuoc phieu muon nay");
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }
}

