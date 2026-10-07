/*
    Trần Quang Hiển
    202418893
 */

package Assignment_5;

public class Projector extends Device {
    private int lumens;
    private int lampHoursUsed;

    public Projector(String id, String name, int yearInService, double purchasePrice,
                     int lumens, int lampHoursUsed) {
        super(id, name, yearInService, purchasePrice);
        if (lumens <= 0) {
            throw new IllegalArgumentException("Do sang phai > 0");
        }
        if (lampHoursUsed < 0) {
            throw new IllegalArgumentException("So gio dung bong den khong duoc am");
        }
        this.lumens = lumens;
        this.lampHoursUsed = lampHoursUsed;
    }

    public int getLumens() { return lumens; }

    public int getLampHoursUsed() { return lampHoursUsed; }

    @Override
    public double calculateMaintenanceCost() {
        double cost = purchasePrice * 0.03;
        if (lampHoursUsed > 3_000) cost += 1_500_000;
        return cost;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Projector: %d lumens, bong den %,d gio",
              lumens, lampHoursUsed);
    }
}