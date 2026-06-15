package repository;

import model.Employee;

import java.util.List;

public interface EmployeeRepository {

    void save(Employee employee);

    Employee findById(int id);

    boolean exists(int id);

    List<Employee> findAll();

    void delete(int id);
}
