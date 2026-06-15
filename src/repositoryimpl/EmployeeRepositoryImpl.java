package repositoryimpl;

import model.Employee;
import repository.EmployeeRepository;

import java.util.*;

public class EmployeeRepositoryImpl
        implements EmployeeRepository {

    private Map<Integer, Employee> employees
            = new HashMap<>();

    @Override
    public void save(Employee employee) {

        employees.put(employee.getUserId(), employee);
    }

    @Override
    public Employee findById(int id) {

        return employees.get(id);
    }

    @Override
    public List<Employee> findAll() {

        return new ArrayList<>(employees.values());
    }

    @Override
    public void delete(int id) {

        employees.remove(id);
    }
}