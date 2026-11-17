/*
    Trần Quang Hiển
    202418893
*/
package Assignment_11;

public final class DoorController {
    private boolean unlocked;

    public void unlockAll() {
        if (!unlocked) {
            unlocked = true;
            System.out.println("[DOOR] All doors UNLOCKED for evacuation");
        }
    }

    public void lockAll() {
        if (unlocked) {
            unlocked = false;
            System.out.println("[DOOR] All doors LOCKED");
        }
    }

    public boolean isUnlocked() {
        return unlocked;
    }
}