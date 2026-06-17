package repositoryimpl;

import database.DBConnection;
import database.DatabaseInitializer;
import enums.EmployeeRole;
import enums.Role;
import model.Bank;
import model.Branch;
import model.Customer;
import model.Employee;
import model.User;
import repository.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    public UserRepositoryImpl() {

        DatabaseInitializer.initialize();
    }

    @Override
    public void save(User user) {

        String sql = "INSERT INTO users "
                + "(user_id, password_hash, full_name, role) "
                + "VALUES (?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE "
                + "password_hash = VALUES(password_hash), "
                + "full_name = VALUES(full_name), "
                + "role = VALUES(role)";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getUserId());
            statement.setString(2, user.getPasswordHash());
            statement.setString(3, user.getFullName());
            statement.setString(4, user.getRole().name());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to save user", exception);
        }
    }

    @Override
    public User findByUserId(String userId) {

        String sql = "SELECT user_id, password_hash, full_name, role "
                + "FROM users WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }
                Role role = Role.valueOf(resultSet.getString("role"));
                if (role == Role.CUSTOMER) {
                    return findCustomerUser(connection, resultSet);
                }
                return findEmployeeUser(connection, resultSet);
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to find user", exception);
        }
    }

    @Override
    public List<User> findAll() {

        String sql = "SELECT user_id FROM users ORDER BY user_id";
        List<User> users = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                users.add(findByUserId(resultSet.getString("user_id")));
            }
            return users;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to list users", exception);
        }
    }

    @Override
    public boolean exists(String userId) {

        String sql = "SELECT 1 FROM users WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to check user", exception);
        }
    }

    @Override
    public void delete(String userId) {

        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, userId);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to delete user", exception);
        }
    }

    private User findCustomerUser(
            Connection connection,
            ResultSet userRow) throws SQLException {

        String sql = "SELECT c.customer_id, c.branch_id, "
                + "br.branch_name, b.bank_id, b.bank_name "
                + "FROM customers c "
                + "LEFT JOIN branches br ON br.branch_id = c.branch_id "
                + "LEFT JOIN banks b ON b.bank_id = br.bank_id "
                + "WHERE c.user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userRow.getString("user_id"));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException("Customer row missing for user "
                            + userRow.getString("user_id"));
                }
                return new Customer(
                        resultSet.getInt("customer_id"),
                        userRow.getString("full_name"),
                        userRow.getString("user_id"),
                        userRow.getString("password_hash"),
                        mapBranch(resultSet));
            }
        }
    }

    private User findEmployeeUser(
            Connection connection,
            ResultSet userRow) throws SQLException {

        String sql = "SELECT e.employee_id, e.employee_role, e.salary, "
                + "br.branch_id, br.branch_name, b.bank_id, b.bank_name "
                + "FROM employees e "
                + "JOIN branches br ON br.branch_id = e.branch_id "
                + "JOIN banks b ON b.bank_id = br.bank_id "
                + "WHERE e.user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userRow.getString("user_id"));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException("Employee row missing for user "
                            + userRow.getString("user_id"));
                }
                return new Employee(
                        resultSet.getInt("employee_id"),
                        userRow.getString("full_name"),
                        userRow.getString("user_id"),
                        userRow.getString("password_hash"),
                        mapBranch(resultSet),
                        EmployeeRole.valueOf(resultSet.getString("employee_role")),
                        resultSet.getDouble("salary"));
            }
        }
    }

    private Branch mapBranch(ResultSet resultSet) throws SQLException {

        int branchId = resultSet.getInt("branch_id");
        if (resultSet.wasNull()) {
            return null;
        }
        Bank bank = new Bank(
                resultSet.getInt("bank_id"),
                resultSet.getString("bank_name"));
        return new Branch(
                branchId,
                resultSet.getString("branch_name"),
                bank);
    }
}
