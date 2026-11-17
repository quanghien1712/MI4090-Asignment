/*
    Trần Quang Hiển
    202418893
*/
package Assignment_11;

public final class Alarm {
    private boolean ringing;

    public void activate() {
        if (!ringing) {
            ringing = true;
            System.out.println("[ALARM] Siren ON");
        }
    }

    public void deactivate() {
        if (ringing) {
            ringing = false;
            System.out.println("[ALARM] Siren OFF");
        }
    }

    public boolean isRinging() {
        return ringing;
    }
}