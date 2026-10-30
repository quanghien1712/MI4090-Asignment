/*
    Trần Quang Hiển
    202418893
*/
package Assignment_8;

@FunctionalInterface
public interface AlertFilter {
    boolean test(DeviceAlertEvent alert);
}