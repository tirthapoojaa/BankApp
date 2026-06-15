package service;

import enums.EmployeeRole;
import model.Branch;
import model.Employee;
import repository.EmployeeRepository;

import java.util.List;

public class EmployeeService {

    private EmployeeRepository employeeRepository;

    private AuthenticationService authenticationService;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            AuthenticationService authenticationService) {

        this.employeeRepository = employeeRepository;
        this.authenticationService = authenticationService;
    }

    public void createEmployee(Employee employee) {

        employeeRepository.save(employee);
    }

    public Employee registerEmployee(
            int employeeId,
            String fullName,
            String userId,
            String password,
            Branch branch,
            EmployeeRole employeeRole) {

        return registerEmployee(
                employeeId,
                fullName,
                userId,
                password,
                branch,
                employeeRole,
                0.0);
    }

    public Employee registerEmployee(
            int employeeId,
            String fullName,
            String userId,
            String password,
            Branch branch,
            EmployeeRole employeeRole,
            double salary) {

        if (employeeId <= 0) {
            throw new IllegalArgumentException("Employee ID must be positive");
        }
        if (employeeRepository.exists(employeeId)) {
            throw new IllegalArgumentException("Employee ID already exists");
        }
        if (branch == null) {
            throw new IllegalArgumentException("Employee branch is required");
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name is required");
        }
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID is required");
        }
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException(
                    "Password must contain at least 8 characters");
        }
        if (employeeRole == null) {
            throw new IllegalArgumentException("Designation is required");
        }
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }

        Employee employee = new Employee(
                employeeId,
                fullName.trim(),
                userId.trim(),
                null,
                branch,
                employeeRole,
                salary);

        authenticationService.register(employee, password);
        employeeRepository.save(employee);
        branch.addEmployee(employee);
        return employee;
    }

    public Employee getEmployee(int id) {

        return employeeRepository.findById(id);
    }

    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }
}
