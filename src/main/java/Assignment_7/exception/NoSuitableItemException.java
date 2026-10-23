/*
    Trần Quang Hiển
    202418893
 */

package Assignment_7.exception;

public class NoSuitableItemException extends Exception {
    public NoSuitableItemException() {
        super("Khong co thiet bi phu hop");
    }

    public NoSuitableItemException(String message) {
        super(message);
    }
}