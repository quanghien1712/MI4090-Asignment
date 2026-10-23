/*
    Trần Quang Hiển
    202418893
 */

package Assignment_7.exception;

public class DuplicateIdException extends Exception {
    private final String duplicateId;

    public DuplicateIdException(String duplicateId) {
        super("Da ton tai phan tu co ma " + duplicateId);
        this.duplicateId = duplicateId;
    }

    public String getDuplicateId() {
        return duplicateId;
    }
}