import java.util.*;

record Employee(String employeeId, String name, double hourlyRate) {
}

record Attendance(String employeeId, int hoursWorked) {
}

class PayrollSystem {
    private final List<Employee> employees;
    private final List<Attendance> attendanceList;

    public PayrollSystem() {
        this.employees = new ArrayList<>();
        this.attendanceList = new ArrayList<>();
    }

    public void addEmployee(String employeeId, String name, double hourlyRate) {
        employees.add(new Employee(employeeId, name, hourlyRate));
    }

    public void recordAttendance(String employeeId, int hoursWorked) {
        attendanceList.add(new Attendance(employeeId, hoursWorked));
    }

    public void processPayroll() {
        System.out.println("Payroll Processing:");
        for (Employee employee : employees) {
            double totalSalary = 0;
            for (Attendance attendance : attendanceList) {
                if (attendance.employeeId().equals(employee.employeeId())) {
                    totalSalary += (employee.hourlyRate() * attendance.hoursWorked());
                }
            }
            double tax = calculateTax(totalSalary);
            double netSalary = totalSalary - tax;

            System.out.println("Employee ID: " + employee.employeeId());
            System.out.println("Employee Name: " + employee.name());
            System.out.println("Total Hours Worked: " + getTotalHoursWorked(employee.employeeId()));
            System.out.println("Total Salary: Rs" + totalSalary);
            System.out.println("Tax Deduction: Rs" + tax);
            System.out.println("Net Salary: Rs" + netSalary);
            System.out.println("----------------------------");
        }
    }

    private double calculateTax(double totalSalary) {
        // Simple tax deduction logic (10% of the salary)
        return 0.1 * totalSalary;
    }

    private int getTotalHoursWorked(String employeeId) {
        int totalHours = 0;
        for (Attendance attendance : attendanceList) {
            if (attendance.employeeId().equals(employeeId)) {
                totalHours += attendance.hoursWorked();
            }
        }
        return totalHours;
    }
}

public class Main {
    public static void main(String[] args) {
        PayrollSystem payrollSystem = new PayrollSystem();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("1. Add Employee");
            System.out.println("2. Record Attendance");
            System.out.println("3. Process Payroll");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Employee ID: ");
                    String employeeId = scanner.next();
                    System.out.print("Enter Employee Name: ");
                    String name = scanner.next();
                    System.out.print("Enter Hourly Rate: ");
                    double hourlyRate = scanner.nextDouble();
                    payrollSystem.addEmployee(employeeId, name, hourlyRate);
                    System.out.println("Employee added successfully!");
                    break;
                case 2:
                    System.out.print("Enter Employee ID: ");
                    String attendanceEmployeeId = scanner.next();
                    System.out.print("Enter Hours Worked: ");
                    int hoursWorked = scanner.nextInt();
                    payrollSystem.recordAttendance(attendanceEmployeeId, hoursWorked);
                    System.out.println("Attendance recorded successfully!");
                    break;
                case 3:
                    payrollSystem.processPayroll();
                    break;
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);
    }
}
