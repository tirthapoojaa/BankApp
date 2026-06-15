package model;

import enums.EmployeeRole;
import enums.Role;

public class Employee extends User {

    private static final long serialVersionUID = 1L;

    private int employeeId;

    private transient Branch branch;

    private EmployeeRole employeeRole;

    private double salary;

    public Employee(
            int employeeId,
            String fullName,
            String userId,
            String passwordHash,
            Branch branch,
            EmployeeRole employeeRole) {

        this(employeeId, fullName, userId, passwordHash,
                branch, employeeRole, 0.0);
    }

    public Employee(
            int employeeId,
            String fullName,
            String userId,
            String passwordHash,
            Branch branch,
            EmployeeRole employeeRole,
            double salary) {

        super(userId, passwordHash, fullName, Role.EMPLOYEE);

        this.employeeId = employeeId;
        this.branch = branch;
        this.employeeRole = employeeRole;
        this.salary = salary;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public Branch getBranch() {
        return branch;
    }

    public EmployeeRole getEmployeeRole() {
        return employeeRole;
    }

    public String getDesignation() {
        return employeeRole.name();
    }

    public double getSalary() {
        return salary;
    }
}
