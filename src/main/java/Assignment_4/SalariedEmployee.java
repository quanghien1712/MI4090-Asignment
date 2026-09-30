package Assignment_4;

public class SalariedEmployee extends Employee {
    private double monthlySalary;
    private double responsibilityAllowance;

    public SalariedEmployee(String id, String name, double monthlySalary) {
        this(id, name, "Unassigned", monthlySalary, 0);
    }

    public SalariedEmployee(String id, String name, String department,
                            double monthlySalary, double responsibilityAllowance) {
        super(id, name, department);
        requireNonNegative(monthlySalary, "Luong thang");
        requireNonNegative(responsibilityAllowance, "Phu cap");
        this.monthlySalary = monthlySalary;
        this.responsibilityAllowance = responsibilityAllowance;
    }

    @Override
    public double calculateGrossPay() {
        return monthlySalary + responsibilityAllowance + getMonthlyBonus();
    }

    @Override
    public String getEmployeeType() { return "Salaried"; }

    @Override
    public void displayPayrollInfo() {
        super.displayPayrollInfo();
        System.out.println("Luong thang: " + String.format("%,.0f", monthlySalary)
              + " | Phu cap: " + String.format("%,.0f", responsibilityAllowance));
    }
}