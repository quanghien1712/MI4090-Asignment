/*
    Trần Quang Hiển
    202418893
*/
package Assignment_8;

public class AirConditionerRemote extends TeachingDevice {
    private final String assignedRoom;
    private boolean responsive = true;

    public AirConditionerRemote(String id, String name, String assignedRoom) {
        super(id, name);
        this.assignedRoom = assignedRoom;
    }

    public String getAssignedRoom() {
        return assignedRoom;
    }
    public boolean isResponsive() {
        return responsive;
    }

    public void updateResponseState(boolean responsive) {
        boolean wasResponsive = this.responsive;
        this.responsive = responsive;
        if (wasResponsive && !responsive) {
            raiseAlert("Air conditioner in room " + assignedRoom + " is not responding", AlertSeverity.CRITICAL);
        }
    }
}