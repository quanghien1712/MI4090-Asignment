/*
    Trần Quang Hiển
    202418893
 */

package Assignment_6;

import java.time.Year;

public abstract class Device {
    protected final String id;
    protected String name;
    protected int yearInService;
    protected double purchasePrice;
    protected DeviceStatus status;
    protected int maxBorrowDays;

    protected Device(String id, String name, int yearInService,
                     double purchasePrice, int maxBorrowDays) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Ma thiet bi khong duoc rong");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Ten thiet bi khong duoc rong");
        }
        if (purchasePrice <= 0) {
            throw new IllegalArgumentException("Gia mua phai > 0");
        }
        if (yearInService > Year.now().getValue()) {
            throw new IllegalArgumentException("Nam dua vao su dung khong đuoc > nam hien tai");
        }
        if (maxBorrowDays <= 0) {
            throw new IllegalArgumentException("So ngay muon toi da phai > 0");
        }
        this.id = id;
        this.name = name;
        this.yearInService = yearInService;
        this.purchasePrice = purchasePrice;
        this.maxBorrowDays = maxBorrowDays;
        this.status = DeviceStatus.AVAILABLE;
    }

    public String getId() {
        return id;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    public int getMaxBorrowDays() {
        return maxBorrowDays;
    }

    public int getYearsInUse() {
        return Year.now().getValue() - yearInService;
    }

    public abstract double calculateAnnualMaintenanceCost();

    public abstract double getLateFeePerDay();

    public void markAsBorrowed() {
        if (status != DeviceStatus.AVAILABLE) {
            throw new IllegalStateException("Chi Borrow duoc khi thiet bi Available, trang thai hien tai: " + status);
        }
        status = DeviceStatus.BORROWED;
    }

    public void markAsAvailable() {
        if (status == DeviceStatus.RETIRED) {
            throw new IllegalStateException("Thiet bi da bi ban");
        }
        status = DeviceStatus.AVAILABLE;
    }

    public void markAsUnderMaintenance() {
        if (status == DeviceStatus.BORROWED || status == DeviceStatus.RETIRED) {
            throw new IllegalStateException("Khong the bao tri thiet bi dang o trang thai " + status);
        }
        status = DeviceStatus.UNDER_MAINTENANCE;
    }

    @Override
    public String toString() {
        return String.format("%s [id=%s, name=%s, year=%d, price=%.0f, status=%s]",
              getClass().getName(), id, name, yearInService, purchasePrice, status);
    }
}
