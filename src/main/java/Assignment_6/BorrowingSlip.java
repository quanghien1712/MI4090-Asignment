/*
    Trần Quang Hiển
    202418893
 */

package Assignment_6;

import Assignment_6.exception.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BorrowingSlip {
    private final String slipId;
    private final IBorrower borrower;
    private final String teachingRoom;
    private final LocalDate borrowedDate;
    private final List<BorrowItem> items = new ArrayList<>();

    public BorrowingSlip(String slipId, IBorrower borrower, String teachingRoom, LocalDate borrowedDate) {
        if (slipId == null || slipId.isEmpty()) {
            throw new IllegalArgumentException("Ma phieu khong duoc rong");
        }
        if (borrower == null) {
            throw new IllegalArgumentException("Nguoi muon khong duoc null");
        }
        if (teachingRoom == null || teachingRoom.isEmpty()) {
            throw new IllegalArgumentException("Phong day khong duoc rong");
        }
        if (borrowedDate == null) {
            throw new IllegalArgumentException("Ngay muon khong duoc null");
        }
        this.slipId = slipId;
        this.borrower = borrower;
        this.teachingRoom = teachingRoom;
        this.borrowedDate = borrowedDate;
    }

    public List<BorrowItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    private BorrowItem findItem(String deviceId) {
        for (BorrowItem item : items) {
            if (item.getDevice().getId().equals(deviceId)) {
                return item;
            }
        }
        return null;
    }

    public void addDevice(Device device, int borrowDays, int fromPeriod, int toPeriod)
          throws BorrowingException {
        validateBorrowing(device, borrowDays);

        BorrowItem item = new BorrowItem(device, borrowedDate, borrowedDate.plusDays(borrowDays),
              fromPeriod, toPeriod);

        device.markAsBorrowed();
        items.add(item);
    }

    public double returnDevice(String deviceId, LocalDate returnedDate) throws BorrowingException {
        if (deviceId == null || deviceId.isBlank()) {
            throw new IllegalArgumentException("Ma thiet bi khong duoc rong");
        }
        if (returnedDate == null) {
            throw new IllegalArgumentException("Ngay tra khong duoc null");
        }
        BorrowItem item = findItem(deviceId);
        if (item == null) {
            throw new DeviceNotInSlipException(deviceId);
        }
        item.returnItem(returnedDate);
        item.getDevice().markAsAvailable();
        return item.calculateLateFee();
    }

    public double calculateTotalLateFee() {
        double total = 0;
        for (BorrowItem item : items) {
            total += item.calculateLateFee();
        }
        return total;
    }

    private void validateBorrowing(Device device, int borrowDays) throws BorrowingException {
        if (device == null) {
            throw new IllegalArgumentException("Thiet bi khong duoc null");
        }
        if (borrowDays <= 0 || borrowDays > device.getMaxBorrowDays()) {
            throw new InvalidBorrowPeriodException(device.getId(), borrowDays, device.getMaxBorrowDays());
        }
        if (findItem(device.getId()) != null) {
            throw new DuplicateDeviceException(device.getId());
        }
        if (items.size() >= borrower.getMaximumDevices()) {
            throw new BorrowLimitExceededException(borrower.getBorrowerId(), borrower.getMaximumDevices());
        }
        if (device.getStatus() != DeviceStatus.AVAILABLE) {
            throw new DeviceUnavailableException(device.getId(), device.getStatus());
        }
    }
}
