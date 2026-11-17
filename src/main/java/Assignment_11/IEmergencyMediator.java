/*
    Trần Quang Hiển
    202418893
*/
package Assignment_11;

public interface IEmergencyMediator {
    String FIRE_DETECTED = "FIRE_DETECTED";
    String ALL_CLEAR = "ALL_CLEAR";

    void notify(String event);
}