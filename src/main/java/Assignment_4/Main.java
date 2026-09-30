/*
    202418893
    Trần Quang Hiển
 */
package Assignment_4;

public class Main {
    static int passed = 0, failed = 0;

    static void check(String name, boolean ok) {
        if (ok) passed++; else failed++;
        System.out.println((ok ? "  [PASS] " : "  [FAIL] ") + name);
    }

    static void checkEquals(String name, double expected, double actual) {
        check(name + " (= " + String.format("%,.0f", actual) + ")", Math.abs(expected - actual) < 0.0001);
    }

    static void checkThrows(String name, Runnable code) {
        try {
            code.run();
            check(name, false);
        } catch (IllegalArgumentException ex) {
            check(name, true);
        }
    }

    static Payroll createSamplePayroll() {
        Payroll payroll = new Payroll("2026-09");

        SalariedEmployee e1 = new SalariedEmployee("E001", "Nguyễn Minh An", "Đào tạo", 15000000, 2000000);
        e1.addBonus(1000000);
        HourlyEmployee e2 = new HourlyEmployee("E002", "Trần Thu Bình", "Hỗ trợ", 100000, 150);
        e2.addBonus(500000, "Du an");
        HourlyEmployee e3 = new HourlyEmployee("E003", "Lê Hoàng Chi", "Hỗ trợ", 100000, 170);
        SalesEmployee e4 = new SalesEmployee("E004", "Phạm Quốc Dũng", "Kinh doanh", 8000000, 200000000, 0.05);
        e4.addBonus(0.02, 50000000, "Ty le");

        payroll.addEmployee(e1);
        payroll.addEmployee(e2);
        payroll.addEmployee(e3);
        payroll.addEmployee(e4);
        return payroll;
    }

    // KB1: Luong co dinh
    static void kb1() {
        System.out.println("KB1: Luong co dinh");
        SalariedEmployee e = new SalariedEmployee("E001", "An", "Dao tao", 15000000, 2000000);
        e.addBonus(1000000);                                       // addBonus(amount)
        checkEquals("Thu nhap = 15tr + 2tr + 1tr", 18000000, e.calculateGrossPay());
    }

    // KB2: Theo gio, khong vuot 160 gio
    static void kb2() {
        System.out.println("KB2: Theo gio, khong vuot 160 gio");
        HourlyEmployee e = new HourlyEmployee("E002", "Binh", "Ho tro", 100000, 150);
        e.addBonus(500000, "Du an");                               // addBonus(amount, reason)
        checkEquals("Thu nhap = 150 x 100k + 500k", 15500000, e.calculateGrossPay());
    }

    // KB3: Theo gio, co gio vuot
    static void kb3() {
        System.out.println("KB3: Theo gio, co gio vuot");
        HourlyEmployee e = new HourlyEmployee("E003", "Chi", "Ho tro", 100000, 170);
        checkEquals("Thu nhap = 160 x 100k + 10 x 100k x 1.5", 17500000, e.calculateGrossPay());
        checkEquals("So gio vuot", 10, e.getOvertimeHours());
    }

    // KB4: Kinh doanh + thuong theo ty le
    static void kb4() {
        System.out.println("KB4: Kinh doanh + thuong theo ty le");
        SalesEmployee e = new SalesEmployee("E004", "Dung", "Kinh doanh", 8000000, 200000000, 0.05);
        e.addBonus(0.02, 50000000, "Ty le");                       // addBonus(rate, ref, reason)
        checkEquals("Thu nhap = 8tr + 10tr + 1tr", 19000000, e.calculateGrossPay());
        e.updateSalesRevenue(300000000);
        checkEquals("Sau khi doanh so len 300tr", 24000000, e.calculateGrossPay());
    }

    // KB5: Bien so gio lam
    static void kb5() {
        System.out.println("KB5: Bien so gio lam");
        checkEquals("0 gio", 0, new HourlyEmployee("H0", "A", "D", 100000, 0).calculateGrossPay());
        checkEquals("160 gio", 16000000, new HourlyEmployee("H1", "A", "D", 100000, 160).calculateGrossPay());
        checkEquals("250 gio", 29500000, new HourlyEmployee("H2", "A", "D", 100000, 250).calculateGrossPay());
        checkThrows("251 gio bi tu choi", () -> new HourlyEmployee("H3", "A", "D", 100000, 251));
        checkThrows("Gio am bi tu choi", () -> new HourlyEmployee("H4", "A", "D", 100000, -1));
    }

    // KB6: addBonus khong hop le
    static void kb6() {
        System.out.println("KB6: addBonus khong hop le");
        SalariedEmployee e = new SalariedEmployee("E1", "A", 1000);
        checkThrows("amount = 0", () -> e.addBonus(0));
        checkThrows("amount am", () -> e.addBonus(-5));
        checkThrows("ly do rong", () -> e.addBonus(100, ""));
        checkThrows("rate = 0.6", () -> e.addBonus(0.6, 100, "r"));
        checkThrows("rate = 0", () -> e.addBonus(0, 100, "r"));
        checkThrows("tham chieu = 0", () -> e.addBonus(0.1, 0, "r"));
        checkEquals("Thuong van bang 0", 0, e.getMonthlyBonus());
    }

    // KB7: Constructor rut gon va du lieu sai
    static void kb7() {
        System.out.println("KB7: Constructor rut gon va du lieu sai");
        SalariedEmployee e = new SalariedEmployee("E1", "A", 1000);
        check("Phong mac dinh la Unassigned", e.getDepartment().equals("Unassigned"));
        checkEquals("Thuong mac dinh", 0, e.getMonthlyBonus());
        checkThrows("Ma rong", () -> new SalariedEmployee("", "A", 1000));
        checkThrows("Ten rong", () -> new SalariedEmployee("E2", "", 1000));
        checkThrows("Luong am", () -> new SalariedEmployee("E3", "A", -1));
        checkThrows("Hoa hong 0.31", () -> new SalesEmployee("E4", "A", "D", 1000, 1000, 0.31));
    }

    // KB8: Tong hop da hinh
    static void kb8() {
        System.out.println("KB8: Tong hop da hinh");
        Payroll payroll = createSamplePayroll();
        payroll.displayPayroll();
        checkEquals("Tong bang luong", 70000000, payroll.calculateTotalPayroll());
        checkEquals("Tong phong Ho tro", 33000000, payroll.calculatePayrollByDepartment("Hỗ trợ"));
        checkEquals("Phong khong ton tai", 0, payroll.calculatePayrollByDepartment("Khong co"));
        check("Nguoi cao nhat la E004", payroll.findHighestPaidEmployee().getEmployeeId().equals("E004"));
    }

    // KB9: Trung ma va tim theo ma
    static void kb9() {
        System.out.println("KB9: Trung ma va tim theo ma");
        Payroll payroll = createSamplePayroll();
        checkThrows("Trung ma E001", () -> payroll.addEmployee(new SalariedEmployee("E001", "Khac", 1)));
        checkThrows("Them null", () -> payroll.addEmployee(null));
        check("Tim thay E002", payroll.findEmployee("E002") != null);
        check("Khong thay X999", payroll.findEmployee("X999") == null);
        checkEquals("Tong khong doi", 70000000, payroll.calculateTotalPayroll());
    }

    // KB10: Bang luong rong va reset thuong
    static void kb10() {
        System.out.println("KB10: Bang luong rong va reset thuong");
        Payroll empty = new Payroll("2026-10");
        checkEquals("Tong bang rong", 0, empty.calculateTotalPayroll());
        check("Khong co nguoi cao nhat", empty.findHighestPaidEmployee() == null);
        empty.displayPayroll();

        SalariedEmployee e = new SalariedEmployee("E1", "A", 1000);
        e.addBonus(500);
        e.resetMonthlyBonus();
        checkEquals("Sau reset thuong", 1000, e.calculateGrossPay());
    }

    public static void main(String[] args) {
        kb1();
        kb2();
        kb3();
        kb4();
        kb5();
        kb6();
        kb7();
        kb8();
        kb9();
        kb10();
        System.out.println("\nPASS: " + passed + " | FAIL: " + failed);
    }
}