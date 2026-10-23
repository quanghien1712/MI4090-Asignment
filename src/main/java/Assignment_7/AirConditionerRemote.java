/*
    Trần Quang Hiển
    202418893
 */

package Assignment_7;

import java.util.Set;

public class AirConditionerRemote extends TeachingDevice {
    private final Set<String> compatibleRooms;

    public AirConditionerRemote(String id, String name, String storageLocation,
                                Set<String> compatibleRooms) {
        super(id, name, storageLocation);
        if (compatibleRooms == null)
            throw new IllegalArgumentException("compatibleRooms khong duoc null");
        this.compatibleRooms = Set.copyOf(compatibleRooms);
    }

    public Set<String> getCompatibleRooms() {
        return compatibleRooms;
    }

    public boolean isCompatibleWith(String roomId) {
        return roomId != null && compatibleRooms.contains(roomId);
    }

    @Override
    public boolean isReadyForUse() {
        return getStatus() == DeviceStatus.AVAILABLE;
    }

    @Override
    public double getDefaultPriorityScore() {
        return compatibleRooms.isEmpty() ? 0 : 100;
    }
}