package service;

import model.Employee;
import repository.EmployeeRepository;

import java.util.List;

public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }

    public void createEmployee(Employee employee) {

        employeeRepository.save(employee);
    }

    public Employee getEmployee(int id) {

        return employeeRepository.findById(id);
    }

    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }
}