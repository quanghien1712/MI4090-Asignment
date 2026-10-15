/*
    Trần Quang Hiển
    202418893
*/

package Assignment_6.exception;

import java.time.LocalDate;

public class DeviceAlreadyReturnedException extends BorrowingException {
    private final String deviceId;
    private final LocalDate returnedDate;

    public DeviceAlreadyReturnedException(String deviceId, LocalDate returnedDate) {
        super("Thiet bi " + deviceId + " da duoc tra vao ngay " + returnedDate);
        this.deviceId = deviceId;
        this.returnedDate = returnedDate;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }
}
