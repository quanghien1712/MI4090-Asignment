/*
    Trần Quang Hiển
    202418893
*/

package Assignment_6.exception;

import java.time.LocalDate;

public class InvalidReturnDateException extends BorrowingException {
    private final LocalDate borrowedDate;
    private final LocalDate returnedDate;

    public InvalidReturnDateException(LocalDate borrowedDate, LocalDate returnedDate) {
        super("Ngay tra " + returnedDate + " khong duoc truoc ngay muon " + borrowedDate);
        this.borrowedDate = borrowedDate;
        this.returnedDate = returnedDate;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }
}
