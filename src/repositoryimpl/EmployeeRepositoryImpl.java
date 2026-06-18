package repositoryimpl;

import database.DBConnection;
import database.DatabaseInitializer;
import enums.EmployeeRole;
import model.Bank;
import model.Branch;
import model.Employee;
import repository.EmployeeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryImpl
        implements EmployeeRepository {

    public EmployeeRepositoryImpl() {

        DatabaseInitializer.initialize();
    }

    @Override
    public void save(Employee employee) {

        String sql = "INSERT INTO employees "
                + "(employee_id, user_id, branch_id, employee_role, salary) "
                + "VALUES (?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE "
                + "user_id = VALUES(user_id), "
                + "branch_id = VALUES(branch_id), "
                + "employee_role = VALUES(employee_role), "
                + "salary = VALUES(salary)";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, employee.getEmployeeId());
            statement.setString(2, employee.getUserId());
            statement.setInt(3, employee.getBranch().getBranchId());
            statement.setString(4, employee.getDesignation());
            statement.setDouble(5, employee.getSalary());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to save employee", exception);
        }
    }

    @Override
    public Employee findById(int id) {

        String sql = "SELECT e.employee_id, e.user_id, "
                + "u.password_hash, u.full_name, e.employee_role, e.salary, "
                + "br.branch_id, br.branch_name, b.bank_id, b.bank_name "
                + "FROM employees e "
                + "JOIN users u ON u.user_id = e.user_id "
                + "JOIN branches br ON br.branch_id = e.branch_id "
                + "JOIN banks b ON b.bank_id = br.bank_id "
                + "WHERE e.employee_id = ?";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapEmployee(resultSet);
                }
            }
            return null;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to find employee", exception);
        }
    }

    @Override
    public boolean exists(int id) {

        String sql = "SELECT 1 FROM employees WHERE employee_id = ?";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to check employee", exception);
        }
    }

    @Override
    public List<Employee> findAll() {

        String sql = "SELECT e.employee_id, e.user_id, "
                + "u.password_hash, u.full_name, e.employee_role, e.salary, "
                + "br.branch_id, br.branch_name, b.bank_id, b.bank_name "
                + "FROM employees e "
                + "JOIN users u ON u.user_id = e.user_id "
                + "JOIN branches br ON br.branch_id = e.branch_id "
                + "JOIN banks b ON b.bank_id = br.bank_id "
                + "ORDER BY e.employee_id";
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                employees.add(mapEmployee(resultSet));
            }
            return employees;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to list employees", exception);
        }
    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM employees WHERE employee_id = ?";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to delete employee", exception);
        }
    }

    private Employee mapEmployee(ResultSet resultSet) throws SQLException {

        Bank bank = new Bank(
                resultSet.getInt("bank_id"),
                resultSet.getString("bank_name"));
        Branch branch = new Branch(
                resultSet.getInt("branch_id"),
                resultSet.getString("branch_name"),
                bank);

        return new Employee(
                resultSet.getInt("employee_id"),
                resultSet.getString("full_name"),
                resultSet.getString("user_id"),
                resultSet.getString("password_hash"),
                branch,
                employeeRole(resultSet.getString("employee_role")),
                resultSet.getDouble("salary"));
    }

    private EmployeeRole employeeRole(String value) {

        if ("RELATION_MANAGER".equals(value)) {
            return EmployeeRole.RELATIONSHIP_MANAGER;
        }
        return EmployeeRole.valueOf(value);
    }
}
