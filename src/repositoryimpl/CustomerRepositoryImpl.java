package repositoryimpl;

import database.DBConnection;
import database.DatabaseInitializer;
import model.Bank;
import model.Branch;
import model.Customer;
import repository.CustomerRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryImpl
        implements CustomerRepository {

    public CustomerRepositoryImpl() {

        DatabaseInitializer.initialize();
    }

    @Override
    public void save(Customer customer) {

        String sql = "INSERT INTO customers "
                + "(customer_id, user_id, branch_id) "
                + "VALUES (?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE "
                + "user_id = VALUES(user_id), "
                + "branch_id = VALUES(branch_id)";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customer.getCustomerId());
            statement.setString(2, customer.getUserId());
            if (customer.getBranch() == null) {
                statement.setNull(3, Types.INTEGER);
            } else {
                statement.setInt(3, customer.getBranch().getBranchId());
            }
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to save customer", exception);
        }
    }

    @Override
    public Customer findById(int id) {

        String sql = "SELECT c.customer_id, c.user_id, "
                + "u.password_hash, u.full_name, c.branch_id, "
                + "br.branch_name, b.bank_id, b.bank_name "
                + "FROM customers c "
                + "JOIN users u ON u.user_id = c.user_id "
                + "LEFT JOIN branches br ON br.branch_id = c.branch_id "
                + "LEFT JOIN banks b ON b.bank_id = br.bank_id "
                + "WHERE c.customer_id = ?";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapCustomer(resultSet);
                }
            }
            return null;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to find customer", exception);
        }
    }

    @Override
    public boolean exists(int id) {

        String sql = "SELECT 1 FROM customers WHERE customer_id = ?";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to check customer", exception);
        }
    }

    @Override
    public List<Customer> findAll() {

        String sql = "SELECT c.customer_id, c.user_id, "
                + "u.password_hash, u.full_name, c.branch_id, "
                + "br.branch_name, b.bank_id, b.bank_name "
                + "FROM customers c "
                + "JOIN users u ON u.user_id = c.user_id "
                + "LEFT JOIN branches br ON br.branch_id = c.branch_id "
                + "LEFT JOIN banks b ON b.bank_id = br.bank_id "
                + "ORDER BY c.customer_id";
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                customers.add(mapCustomer(resultSet));
            }
            return customers;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to list customers", exception);
        }
    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM customers WHERE customer_id = ?";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to delete customer", exception);
        }
    }

    private Customer mapCustomer(ResultSet resultSet) throws SQLException {

        return new Customer(
                resultSet.getInt("customer_id"),
                resultSet.getString("full_name"),
                resultSet.getString("user_id"),
                resultSet.getString("password_hash"),
                mapBranch(resultSet));
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
