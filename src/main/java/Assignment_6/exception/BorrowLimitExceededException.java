/*
    Trần Quang Hiển
    202418893
 */

package Assignment_6.exception;

public class BorrowLimitExceededException extends BorrowingException {
    private final String borrowerId;
    private final int maximumDevices;

    public BorrowLimitExceededException(String borrowerId, int maximumDevices) {
        super("Nguoi muon " + borrowerId + " da dat gioi han " + maximumDevices + " thiet bi tren 1 phieu");
        this.borrowerId = borrowerId;
        this.maximumDevices = maximumDevices;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public int getMaximumDevices() {
        return maximumDevices;
    }
}