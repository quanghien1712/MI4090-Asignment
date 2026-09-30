/*
    202418893
    Trần Quang Hiển
 */
package Assignment_4;

public class HourlyEmployee extends Employee {
    private static final double STANDARD_HOURS = 160;
    private static final double MAX_HOURS = 250;

    private double hourlyRate;
    private double workedHours;

    public HourlyEmployee(String id, String name, double hourlyRate) {
        this(id, name, "Unassigned", hourlyRate, 0);
    }

    public HourlyEmployee(String id, String name, String department,
                          double hourlyRate, double workedHours) {
        super(id, name, department);
        requireNonNegative(hourlyRate, "Don gia gio");
        if (workedHours < 0 || workedHours > MAX_HOURS)
            throw new IllegalArgumentException("So gio lam phai tu 0 den 250");
        this.hourlyRate = hourlyRate;
        this.workedHours = workedHours;
    }

    public double getOvertimeHours() {
        return Math.max(0, workedHours - STANDARD_HOURS);
    }

    public double calculateBasePay() {
        double regularHours = Math.min(workedHours, STANDARD_HOURS);
        return regularHours * hourlyRate + getOvertimeHours() * hourlyRate * 1.5;
    }

    @Override
    public double calculateGrossPay() {
        return calculateBasePay() + getMonthlyBonus();
    }

    @Override
    public String getEmployeeType() { return "Hourly"; }

    @Override
    public void displayPayrollInfo() {
        super.displayPayrollInfo();
        System.out.println("Don gia: " + String.format("%,.0f", hourlyRate)
              + " | Gio lam: " + workedHours + " | Gio vuot: " + getOvertimeHours());
    }
}