/*
    MSSV 202418893
    Ho Ten: Tran Quang Hien
 */

public class SoftwareEngineer extends Employee {
    private String primaryLanguage;
    private double technicalAllowance;

    private static void validateEngineer(String lang, double allowance) {
        if (lang == null || lang.isEmpty())
            throw new IllegalArgumentException("Ngon ngu chinh khong duoc rong");
        if (allowance < 0)
            throw new IllegalArgumentException("Phu cap khong duoc am");
    }

    public SoftwareEngineer(String id, String fullName, String primaryLanguage) {
        super(id, fullName);
        validateEngineer(primaryLanguage, 0);
        this.primaryLanguage = primaryLanguage;
        this.technicalAllowance = 0;
    }

    public SoftwareEngineer(String id, String fullName, double baseSalary,
                            String primaryLanguage, double technicalAllowance) {
        super(id, fullName, baseSalary);
        validateEngineer(primaryLanguage, technicalAllowance);
        this.primaryLanguage = primaryLanguage;
        this.technicalAllowance = technicalAllowance;
    }

    @Override
    public double calculateMonthlyCost() {
        return baseSalary + technicalAllowance;
    }

    @Override
    public void displayInfo() {
        System.out.printf("SoftwareEngineer: ID=%s, Ten=%s, NgonNguChinh=%s, Luong=%.1f, PhuCap=%.1f, ChiPhi/Thang=%.1f%n",
              id, fullName, primaryLanguage, baseSalary, technicalAllowance, calculateMonthlyCost());
    }

    @Override
    protected void finalize() {
        System.out.println("[Destructor] SoftwareEngineer " + id + " bi huy");
    }
}