/*
    Trần Quang Hiển
    202418893
*/
package Assignment_8;

public class InvalidDeviceReadingException extends RuntimeException {
    private final String deviceId;
    private final int invalidValue;

    public InvalidDeviceReadingException(String deviceId, int invalidValue, String message) {
        super(message);
        this.deviceId = deviceId;
        this.invalidValue = invalidValue;
    }

    public String getDeviceId() {
        return deviceId;
    }
    public int getInvalidValue() {
        return invalidValue;
    }
}