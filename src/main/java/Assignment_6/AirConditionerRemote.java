/*
    Trần Quang Hiển
    202418893
 */

package Assignment_6;

public class AirConditionerRemote extends Device {
    private static final int MAX_BORROW_DAYS = 5;
    private static final double LATE_FEE_PER_DAY = 8000;
    private static final double MAINTENANCE_RATE = 0.08;

    public AirConditionerRemote(String id, String name, int yearInService, double purchasePrice) {
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
