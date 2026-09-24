/*
    MSSV 202418893
    Ho Ten: Tran Quang Hien
 */

import java.util.ArrayList;
import java.util.List;

public class ProjectTeam implements AutoCloseable {
    private String projectCode;
    private String projectName;
    private Employee leader;
    private List<Employee> members;

    public ProjectTeam(String projectCode, String projectName) {
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.leader = null;
        this.members = new ArrayList<>();
    }

    public ProjectTeam(String projectCode, String projectName, Employee leader) {
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.leader = leader;
        this.members = new ArrayList<>();
        this.members.add(leader);
    }

    public boolean contains(String employeeId) {
        return members.stream().anyMatch(e -> e.getId().equals(employeeId));
    }

    public boolean addMember(Employee employee) {
        if (contains(employee.getId())) return false;
        members.add(employee);
        return true;
    }

    public boolean addMember(Employee employee, boolean makeLeader) {
        boolean added = true;
        if (!contains(employee.getId())) {
            members.add(employee);
        } else {
            added = false;
        }
        if (makeLeader) {
            leader = employee;
        }
        return added;
    }

    public boolean removeMember(String employeeId) {
        if (leader != null && leader.getId().equals(employeeId)) {
            System.out.println("Khong the xoa truong nhom khi chua chon nguoi thay the");
            return false;
        }
        return members.removeIf(e -> e.getId().equals(employeeId));
    }

    public void changeLeader(Employee employee) {
        if (!contains(employee.getId())) {
            members.add(employee);
        }
        leader = employee;
    }

    public double calculateTotalMonthlyCost() {
        double total = 0;
        for (Employee e : members) total += e.calculateMonthlyCost();
        return total;
    }

    public void displayTeam() {
        System.out.println("=== Nhom du an: " + projectCode + " - " + projectName + " ===");
        System.out.println("Truong nhom: " + (leader != null ? leader.getFullName() : "(chua co)"));
        System.out.println("Danh sach thanh vien (" + members.size() + "):");
        for (Employee e : members) {
            e.displayInfo();
        }
        System.out.printf("Tong chi phi hang thang: %.1f%n%n", calculateTotalMonthlyCost());
    }

    @Override
    public void close() {
        System.out.println("[Destructor] ProjectTeam " + projectCode + " bi huy");
        members.clear();
    }
}