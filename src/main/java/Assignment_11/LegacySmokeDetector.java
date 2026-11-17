/*
    Trần Quang Hiển
    202418893
*/
package Assignment_11;

public final class LegacySmokeDetector {
    private int smokeLevel;

    public LegacySmokeDetector(int smokeLevel) {
        this.smokeLevel = smokeLevel;
    }

    public int getSmokeLevel() {
        return smokeLevel;//0..1000
    }

    public String getDescription() {
        return "Legacy smoke detector";
    }

    public void setSmokeLevel(int value) {
        smokeLevel = Math.max(0, Math.min(1000, value));
    }
}