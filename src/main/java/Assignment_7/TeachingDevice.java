/*
    Trần Quang Hiển
    202418893
 */

package Assignment_7;

import Assignment_7.exception.DeviceNotReadyException;

public abstract class TeachingDevice implements IIdentifiable {
    private String id;
    private String name;
    private String storageLocation;
    private DeviceStatus status;

    protected TeachingDevice(String id, String name, String storageLocation) {
        if (id == null || id.isEmpty())
            throw new IllegalArgumentException("Id khong hop le");
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Name khong hop le");
        if (storageLocation == null || storageLocation.isEmpty())
            throw new IllegalArgumentException("StorageLocation khong hop le");
        this.id = id;
        this.name = name;
        this.storageLocation = storageLocation;
        this.status = DeviceStatus.AVAILABLE;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    protected void setStatus(DeviceStatus status) {
        this.status = status;
    }

    public abstract boolean isReadyForUse();

    public abstract double getDefaultPriorityScore();

    public void borrow() throws DeviceNotReadyException {
        if (!isReadyForUse())
            throw new DeviceNotReadyException(id, status);
        setStatus(DeviceStatus.BORROWED);
    }

    public void returnDevice() {
        if (status != DeviceStatus.BORROWED)
            throw new IllegalStateException("Khong the tra thiet bi " + id + " vi trang thai hien tai la " + status);
        setStatus(DeviceStatus.AVAILABLE);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + id + "] " + name + " - " + status;
    }
}