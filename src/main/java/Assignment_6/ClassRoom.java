/*
    Trần Quang Hiển
    202418893
 */

package Assignment_6;

import java.util.ArrayList;
import java.util.List;

public class ClassRoom {
    private static final int MAINTENANCE_YEARS_THRESHOLD = 5;

    private final String roomId;
    private String roomName;
    private int capacity;
    private final List<Device> devices = new ArrayList<>();

    public ClassRoom(String roomId, String roomName, int capacity) {
        if (roomId == null || roomId.isEmpty()) {
            throw new IllegalArgumentException("Ma phong khong duoc rong");
        }
        if (roomName == null || roomName.isEmpty()) {
            throw new IllegalArgumentException("Ten phong khong duoc rong");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Suc chua phai > 0");
        }
        this.roomId = roomId;
        this.roomName = roomName;
        this.capacity = capacity;
    }

    public Device findDevice(String deviceId) {
        for (Device d : devices) {
            if (d.getId().equals(deviceId)) {
                return d;
            }
        }
        return null;
    }

    public void addDevice(Device device) {
        if (device == null) {
            throw new IllegalArgumentException("Thiet bi khong duoc null");
        }
        if (findDevice(device.getId()) != null) {
            throw new IllegalArgumentException("Phong da co thiet bi ma " + device.getId());
        }
        devices.add(device);
    }

    public boolean removeDevice(String deviceId) {
        Device found = findDevice(deviceId);
        return found != null && devices.remove(found);
    }

    public double calculateAnnualMaintenanceCost() {
        double total = 0;
        for (Device d : devices) {
            total += d.calculateAnnualMaintenanceCost();
        }
        return total;
    }

    public List<Device> getDevicesRequiringMaintenance() {
        List<Device> result = new ArrayList<>();
        for (Device d : devices) {
            if (d.getStatus() == DeviceStatus.UNDER_MAINTENANCE
                  || d.getYearsInUse() > MAINTENANCE_YEARS_THRESHOLD) {
                result.add(d);
            }
        }
        return result;
    }
}
