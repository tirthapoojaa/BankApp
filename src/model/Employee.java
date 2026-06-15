package model;

import enums.EmployeeRole;

public class Employee extends User {

    private Branch branch;

    private EmployeeRole role;

    public Employee(
            int userId,
            String name,
            String username,
            String password,
            Branch branch,
            EmployeeRole role) {

        super(userId, name, username, password);

        this.branch = branch;
        this.role = role;
    }
}