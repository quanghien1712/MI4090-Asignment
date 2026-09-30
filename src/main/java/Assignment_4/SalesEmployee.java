package Assignment_4;

public class SalesEmployee extends Employee {
    private double baseSalary;
    private double salesRevenue;
    private double commissionRate;

    public SalesEmployee(String id, String name, double baseSalary) {
        this(id, name, "Unassigned", baseSalary, 0, 0);
    }

    public SalesEmployee(String id, String name, String department,
                         double baseSalary, double salesRevenue, double commissionRate) {
        super(id, name, department);
        requireNonNegative(baseSalary, "Luong co ban");
        requireNonNegative(salesRevenue, "Doanh so");
        if (commissionRate < 0 || commissionRate > 0.3)
            throw new IllegalArgumentException("Hoa hong phai tu 0 den 0.3");
        this.baseSalary = baseSalary;
        this.salesRevenue = salesRevenue;
        this.commissionRate = commissionRate;
    }

    public void updateSalesRevenue(double newRevenue) {
        requireNonNegative(newRevenue, "Doanh so");
        this.salesRevenue = newRevenue;
    }

    @Override
    public double calculateGrossPay() {
        return baseSalary + salesRevenue * commissionRate + getMonthlyBonus();
    }

    @Override
    public String getEmployeeType() { return "Sales"; }

    @Override
    public void displayPayrollInfo() {
        super.displayPayrollInfo();
        System.out.println("Luong co ban: " + String.format("%,.0f", baseSalary)
              + " | Doanh so: " + String.format("%,.0f", salesRevenue)
              + " | Hoa hong: " + (commissionRate * 100) + "%");
    }
}