/*
    Trần Quang Hiển
    202418893
 */

package Assignment_6.exception;

public class BorrowingException extends Exception {
    public BorrowingException(String message) {
        super(message);
    }

    public BorrowingException(String message, Exception cause) {
        super(message, cause);
    }
}
