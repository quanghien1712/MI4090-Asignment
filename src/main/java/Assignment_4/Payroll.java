/*
    202418893
    Trần Quang Hiển
 */
package Assignment_4;

import java.util.ArrayList;
import java.util.List;

public class Payroll {
    private String period;
    private List<Employee> employees = new ArrayList<>();

    public Payroll(String period) {
        this.period = period;
    }

    public void addEmployee(Employee employee) {
        if (employee == null)
            throw new IllegalArgumentException("Nhan su khong duoc null");
        if (findEmployee(employee.getEmployeeId()) != null)
            throw new IllegalArgumentException("Trung ma nhan su: " + employee.getEmployeeId());
        employees.add(employee);
    }

    public Employee findEmployee(String employeeId) {
        for (Employee e : employees) {
            if (e.getEmployeeId().equals(employeeId)) return e;
        }
        return null;
    }

    public double calculateTotalPayroll() {
        double total = 0;
        for (Employee e : employees) total += e.calculateGrossPay();
        return total;
    }

    public double calculatePayrollByDepartment(String department) {
        double total = 0;
        for (Employee e : employees) {
            if (e.getDepartment().equals(department)) total += e.calculateGrossPay();
        }
        return total;
    }

    public Employee findHighestPaidEmployee() {
        Employee best = null;
        for (Employee e : employees) {
            if (best == null || e.calculateGrossPay() > best.calculateGrossPay()) best = e;
        }
        return best;
    }

    public void displayPayroll() {
        System.out.println("===== BANG LUONG " + period + " =====");
        if (employees.isEmpty()) {
            System.out.println("(Bang luong trong)");
            return;
        }
        for (Employee e : employees) e.displayPayrollInfo();
        System.out.println("TONG: " + String.format("%,.0f", calculateTotalPayroll()));
    }
}