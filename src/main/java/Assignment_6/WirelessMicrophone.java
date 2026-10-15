/*
    Trần Quang Hiển
    202418893
 */

package Assignment_6;

public class WirelessMicrophone extends Device {
    private static final int MAX_BORROW_DAYS = 1;
    private static final double LATE_FEE_PER_DAY = 15000;
    private static final double MAINTENANCE_RATE = 0.10;

    public WirelessMicrophone(String id, String name, int yearInService, double purchasePrice) {
        super(id, name, yearInService, purchasePrice, MAX_BORROW_DAYS);
    }

    @Override
    public double calculateAnnualMaintenanceCost() {
        return purchasePrice * MAINTENANCE_RATE;
    }

    @Override
    public double getLateFeePerDay() {
        return LATE_FEE_PER_DAY;
    }
}

