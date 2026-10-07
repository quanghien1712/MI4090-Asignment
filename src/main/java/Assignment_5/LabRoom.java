/*
    Trần Quang Hiển
    202418893
 */

package Assignment_5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LabRoom {
    private String roomId;
    private String name;
    private int capacity;
    private List<Device> devices;

    public LabRoom(String roomId, String name, int capacity) {
        if (roomId == null || roomId.isEmpty()) {
            throw new IllegalArgumentException("Ma phong khong duoc rong");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Ten phong khong duoc rong");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Suc chua phai > 0");
        }
        this.roomId = roomId;
        this.name = name;
        this.capacity = capacity;
        this.devices = new ArrayList<>();
    }

    public String getRoomId() { return roomId; }

    public String getName() { return name; }

    public int getCapacity() { return capacity; }

    // Tranh sua truc tiep tu ben ngoai
    public List<Device> getDevices() {
        return Collections.unmodifiableList(devices);
    }

    public void addDevice(Device device) {
        if (device == null) {
            throw new IllegalArgumentException("Thiet bi khong duoc null");
        }
        if (findDevice(device.getId()) != null) {
            throw new IllegalArgumentException("Phong " + roomId + " da co thiet bi ma " + device.getId());
        }
        devices.add(device);
    }

    public boolean removeDevice(String deviceId) {
        Device d = findDevice(deviceId);
        return d != null && devices.remove(d);
    }

    public Device findDevice(String deviceId) {
        if (deviceId == null) return null;
        for (Device d : devices) {
            if (d.getId().equals(deviceId)) return d;
        }
        return null;
    }

    public double calculateAnnualMaintenanceCost() {
        double total = 0;
        for (Device d : devices) {
            total += d.calculateMaintenanceCost();
        }
        return total;
    }

    public List<Device> getDevicesRequiringMaintenance() {
        List<Device> result = new ArrayList<>();
        for (Device d : devices) {
            if (d.getStatus() == DeviceStatus.UnderMaintenance || d.getYearsInUse() > 5) {
                result.add(d);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return String.format("Phong %s - %s (suc chua %d, %d thiet bi)", roomId, name, capacity, devices.size());
    }
}