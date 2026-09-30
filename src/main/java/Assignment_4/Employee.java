/*
    202418893
    Trần Quang Hiển
 */
package Assignment_4;

public abstract class Employee {
    private String employeeId;
    private String fullName;
    private String department;
    private double monthlyBonus;

    public Employee(String employeeId, String fullName) {
        this(employeeId, fullName, "Unassigned");
    }

    public Employee(String employeeId, String fullName, String department) {
        requireText(employeeId, "Ma nhan su");
        requireText(fullName, "Ho ten");
        requireText(department, "Phong ban");
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.department = department;
        this.monthlyBonus = 0;
    }

    protected static void requireText(String value, String name) {
        if (value == null || value.trim().isEmpty())
            throw new IllegalArgumentException(name + " khong duoc rong");
    }

    protected static void requireNonNegative(double value, String name) {
        if (value < 0)
            throw new IllegalArgumentException(name + " khong duoc am");
    }

    public String getEmployeeId() { return employeeId; }
    public String getDepartment() { return department; }
    public double getMonthlyBonus() { return monthlyBonus; }

    public void addBonus(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Khoan thuong phai lon hon 0");
        monthlyBonus += amount;
    }

    public void addBonus(double amount, String reason) {
        requireText(reason, "Ly do");
        addBonus(amount);
    }

    public void addBonus(double rate, double referenceAmount, String reason) {
        if (rate <= 0 || rate > 0.5)
            throw new IllegalArgumentException("Ty le thuong phai trong (0, 0.5]");
        if (referenceAmount <= 0)
            throw new IllegalArgumentException("Gia tri tham chieu phai lon hon 0");
        addBonus(rate * referenceAmount, reason);
    }

    public void resetMonthlyBonus() { monthlyBonus = 0; }

    public abstract double calculateGrossPay();
    public abstract String getEmployeeType();

    public void displayPayrollInfo() {
        System.out.println("[" + getEmployeeType() + "] " + employeeId + " - " + fullName
              + " - " + department);
        System.out.println("Thuong: " + String.format("%,.0f", monthlyBonus));
        System.out.println("Thu nhap: " + String.format("%,.0f", calculateGrossPay()));
    }
}