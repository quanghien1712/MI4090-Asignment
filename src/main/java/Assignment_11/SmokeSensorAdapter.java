/*
    Trần Quang Hiển
    202418893
*/
package Assignment_11;

import java.util.Objects;

public final class SmokeSensorAdapter implements Sensor {
    private final LegacySmokeDetector legacy;

    public SmokeSensorAdapter(LegacySmokeDetector legacy) {
        this.legacy = Objects.requireNonNull(legacy, "legacy must not be null");
    }

    @Override
    public double readValue() {
        return legacy.getSmokeLevel() / 10.0; //0..1000 -> 0..100
    }

    @Override
    public String getUnit() {
        return "% smoke";
    }
}