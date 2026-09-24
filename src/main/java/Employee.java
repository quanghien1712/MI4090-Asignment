/*
    MSSV 202418893
    Ho Ten: Tran Quang Hien
 */

public class Employee {
    protected String id;
    protected String fullName;
    protected double baseSalary;

    private static void validateIdName(String id, String fullName) {
        if (id == null || id.isEmpty())
            throw new IllegalArgumentException("Ma nhan su khong duoc rong");
        if (fullName == null || fullName.isEmpty())
            throw new IllegalArgumentException("Ho ten khong duoc rong");
    }

    private static void validateSalary(double salary) {
        if (salary < 0)
            throw new IllegalArgumentException("Luong co ban khong duoc am");
    }

    public Employee() {
        this.id = "UNKNOWN";
        this.fullName = "Unnamed employee";
        this.baseSalary = 0;
    }

    public Employee(String id, String fullName) {
        validateIdName(id, fullName);
        this.id = id;
        this.fullName = fullName;
        this.baseSalary = 0;
    }

    public Employee(String id, String fullName, double baseSalary) {
        validateIdName(id, fullName);
        validateSalary(baseSalary);
        this.id = id;
        this.fullName = fullName;
        this.baseSalary = baseSalary;
    }

    public void increaseSalary(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("So tien tang phai duong");
        this.baseSalary += amount;
    }

    public void increaseSalary(double value, boolean byPercentage) {
        if (value <= 0)
            throw new IllegalArgumentException("Gia tri tang phai duong");
        if (byPercentage)
            this.baseSalary += this.baseSalary * value / 100.0;
        else
            this.baseSalary += value;
    }

    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public double getBaseSalary() { return baseSalary; }

    public double calculateMonthlyCost() {
        return baseSalary;
    }

    public void displayInfo() {
        System.out.printf("Employee: ID=%s, Ten=%s, Luong=%.1f, ChiPhi/Thang=%.1f%n",
              id, fullName, baseSalary, calculateMonthlyCost());
    }

    @Override
    protected void finalize() {
        System.out.println("[Destructor] Employee " + id + " bi huy");
    }
}