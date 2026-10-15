/*
    Trần Quang Hiển
    202418893
*/

package Assignment_6.exception;

public class DuplicateDeviceException extends BorrowingException {
    private final String deviceId;

    public DuplicateDeviceException(String deviceId) {
        super("Thiet bi " + deviceId + " da co trong phieu muon");
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }
}

