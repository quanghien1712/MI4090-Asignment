/*
    Trần Quang Hiển
    202418893
 */

package Assignment_6;

import Assignment_6.exception.BorrowingException;
import Assignment_6.exception.DeviceAlreadyReturnedException;
import Assignment_6.exception.InvalidReturnDateException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BorrowItem {
    private final Device device;
    private final LocalDate borrowedDate;
    private final LocalDate dueDate;
    private int fromPeriod;
    private int toPeriod;
    private LocalDate returnedDate;

    public BorrowItem(Device device, LocalDate borrowedDate, LocalDate dueDate,
                      int fromPeriod, int toPeriod) {
        if (device == null) {
            throw new IllegalArgumentException("Thiet bi khong duoc null");
        }
        if (borrowedDate == null || dueDate == null) {
            throw new IllegalArgumentException("Ngay muon va ngay tra khong duoc null");
        }
        if (dueDate.isBefore(borrowedDate)) {
            throw new IllegalArgumentException("Ngay hen tra phai sau hoac bang ngay muon");
        }
        if (fromPeriod <= 0 || toPeriod < fromPeriod) {
            throw new IllegalArgumentException("Tiet muon khong hop le: tu tiet " + fromPeriod + " den tiet " + toPeriod);
        }
        this.device = device;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
        this.fromPeriod = fromPeriod;
        this.toPeriod = toPeriod;
        this.returnedDate = null;
    }

    public boolean isLate() {
        return isReturned() && returnedDate.isAfter(dueDate);
    }

    public Device getDevice() {
        return device;
    }

    public boolean isReturned() {
        return returnedDate != null;
    }

    public void returnItem(LocalDate returnedDate) throws BorrowingException {
        if (returnedDate == null) {
            throw new IllegalArgumentException("Ngay tra khong duoc null");
        }
        if (isReturned()) {
            throw new DeviceAlreadyReturnedException(device.getId(), this.returnedDate);
        }
        if (returnedDate.isBefore(borrowedDate)) {
            throw new InvalidReturnDateException(borrowedDate, returnedDate);
        }
        this.returnedDate = returnedDate;
    }

    public double calculateLateFee() {
        if (!isReturned()) {
            return 0;
        }
        double lateDays = Math.max(0, ChronoUnit.DAYS.between(dueDate, returnedDate));
        return lateDays * device.getLateFeePerDay();
    }

    @Override
    public String toString() {
        return String.format("%s | muon %s -> han %s | tiet %d-%d | tra: %s",
              device.getId(), borrowedDate, dueDate, fromPeriod, toPeriod,
              isReturned() ? returnedDate : "chua tra");
    }
}