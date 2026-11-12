/*
    Trần Quang Hiển
    202418893
*/
package Assignment_10;

public final class DeviceFactory {
    private DeviceFactory() {
    }

    public static Device create(String type, String name) {
        if (type == null) {
            throw new IllegalArgumentException("Device type must not be null");
        }
        return switch (type.trim().toLowerCase()) {
            case "light" -> new SmartLight(name);
            case "fan" -> new SmartFan(name);
            case "door" -> new SmartDoor(name);
            default -> throw new IllegalArgumentException("Unsupported device type: " + type);
        };
    }
}