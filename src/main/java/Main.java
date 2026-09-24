/*
    MSSV 202418893
    Ho Ten: Tran Quang Hien
*/

public class Main {
    public static void main(String[] args) {
        // 1. 2 Employee bang 2 constructor khac nhau
        Employee e1 = new Employee("NV001", "Lam Quy Do");
        Employee e2 = new Employee("NV002", "Tran Thi Huong", 8000000);
        e1.displayInfo();
        e2.displayInfo();

        // 2. 2 SoftwareEngineer bang 2 constructor khac nhau
        SoftwareEngineer se1 = new SoftwareEngineer("NV003", "Tran Quang Hien", "Java");
        SoftwareEngineer se2 = new SoftwareEngineer("NV004", "Bui Ngoc Tuyet", 12000000, "Python", 2000000);
        se1.displayInfo();
        se2.displayInfo();

        // 3. Tang luong co dinh
        e1.increaseSalary(1000000);
        System.out.println("Sau tang co dinh, luong employee 1 = " + e1.getBaseSalary());

        // 4. Tang luong theo phan tram
        e2.increaseSalary(10, true);
        System.out.println("Sau tang 10%, luong employee 2 = " + e2.getBaseSalary() + "\n");

        // 5. Tao nhom du an chua co truong nhom
        ProjectTeam team1 = new ProjectTeam("DA01", "He thong quan ly nhan su");
        team1.displayTeam();

        // 6. Them nhan su bang addMember(employee)
        team1.addMember(e1);
        team1.displayTeam();

        // 7. Them ky su bang addMember(employee, true) de dat lam truong nhom
        team1.addMember(se1, true);
        team1.displayTeam();

        // 8. Thu them lai thanh vien da ton tai
        boolean result = team1.addMember(e1);
        System.out.println("Them lai employee 1 (da co): " + (result ? "thanh cong" : "that bai (dung)"));

        // 9. Hien thi danh sach bang loi goi da hinh
        team1.displayTeam();

         //10. Tinh tong chi phi
        System.out.println("Tong chi phi rieng: " + team1.calculateTotalMonthlyCost() + "\n");

        // 11. Thu xoa truong nhom hien tai -> phai bi tu choi
        boolean removeLeader = team1.removeMember(se1.getId());
        System.out.println("Ket qua xoa truong nhom: " + (removeLeader ? "thanh cong (SAI)" : "bi tu choi (dung)"));

        // 12. Doi truong nhom roi xoa nguoi tung la truong nhom
        team1.changeLeader(e1);
        System.out.println("Da doi truong nhom sang employee 1.");
        boolean removed = team1.removeMember(se1.getId());
        System.out.println("Xoa softwareEngineer1 (khong con la truong nhom): " + (removed ? "thanh cong" : "that bai"));
        team1.displayTeam();

        // 13. Tao nhom thu hai, them nhan su da co o nhom 1 -> ket tap nhieu nhom
        try (ProjectTeam team2 = new ProjectTeam("DA02", "Cong cu noi bo")) {
            team2.addMember(e2, true);
            team2.addMember(e1);
            team2.displayTeam();
        }

        // 15. Chung minh e1, e2 van ton tai sau khi team2 bi huy
        System.out.println("Sau khi team2 bi huy, employee 1 van ton tai: " + e1.getFullName());
        System.out.println("employee 2 van ton tai: " + e2.getFullName());
    }
}