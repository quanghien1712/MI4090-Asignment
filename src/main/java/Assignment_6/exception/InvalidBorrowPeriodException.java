/*
    Trần Quang Hiển
    202418893
*/

package Assignment_6.exception;

public class InvalidBorrowPeriodException extends BorrowingException {
    private final String deviceId;
    private final int requestedDays;
    private final int maxDays;

    public InvalidBorrowPeriodException(String deviceId, int requestedDays, int maxDays) {
        super("Thoi han muon khong hop le cho thiet bi " + deviceId
              + ": yeu cau " + requestedDays + " ngay, toi da " + maxDays + " ngay");
        this.deviceId = deviceId;
        this.requestedDays = requestedDays;
        this.maxDays = maxDays;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public int getRequestedDays() {
        return requestedDays;
    }

    public int getMaxDays() {
        return maxDays;
    }
}
