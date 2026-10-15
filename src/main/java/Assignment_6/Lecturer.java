/*
    Trần Quang Hiển
    202418893
 */

package Assignment_6;

public class Lecturer implements IBorrower {
    private static final int MAX_DEVICES = 3;

    private final String borrowerId;
    private final String fullName;

    public Lecturer(String borrowerId, String fullName) {
        if (borrowerId == null || borrowerId.isEmpty()) {
            throw new IllegalArgumentException("Ma nguoi muon khong duoc rong");
        }
        if (fullName == null || fullName.isEmpty()) {
            throw new IllegalArgumentException("Ho ten khong duoc rong");
        }
        this.borrowerId = borrowerId;
        this.fullName = fullName;
    }

    @Override
    public String getBorrowerId() {
        return borrowerId;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public int getMaximumDevices() {
        return MAX_DEVICES;
    }
}