package repositoryimpl;

import database.DBConnection;
import database.DatabaseInitializer;
import enums.AccountStatus;
import model.Account;
import model.Bank;
import model.Branch;
import model.CurrentAccount;
import model.Customer;
import model.SavingsAccount;
import repository.AccountRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryImpl
        implements AccountRepository {

    public AccountRepositoryImpl() {

        DatabaseInitializer.initialize();
    }

    @Override
    public void save(Account account) {

        String sql = "INSERT INTO accounts "
                + "(account_id, customer_id, branch_id, account_type, balance, status) "
                + "VALUES (?, ?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE "
                + "customer_id = VALUES(customer_id), "
                + "branch_id = VALUES(branch_id), "
                + "account_type = VALUES(account_type), "
                + "balance = VALUES(balance), "
                + "status = VALUES(status)";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, account.getAccountId());
            statement.setInt(2, account.getCustomer().getCustomerId());
            statement.setInt(3, account.getBranch().getBranchId());
            statement.setString(4, account.getAccountType().toUpperCase());
            statement.setDouble(5, account.getBalance());
            statement.setString(6, account.getStatus() == null
                    ? AccountStatus.ACTIVE.name()
                    : account.getStatus().name());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to save account", exception);
        }
    }

    @Override
    public Account findById(int id) {

        String sql = baseQuery() + " WHERE a.account_id = ?";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapAccount(resultSet);
                }
            }
            return null;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to find account", exception);
        }
    }

    @Override
    public List<Account> findByCustomerId(int customerId) {

        return findByColumn("a.customer_id", customerId);
    }

    @Override
    public List<Account> findByBranchId(int branchId) {

        return findByColumn("a.branch_id", branchId);
    }

    @Override
    public List<Account> findAll() {

        String sql = baseQuery() + " ORDER BY a.account_id";
        List<Account> accounts = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                accounts.add(mapAccount(resultSet));
            }
            return accounts;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to list accounts", exception);
        }
    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM accounts WHERE account_id = ?";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to delete account", exception);
        }
    }

    private List<Account> findByColumn(String column, int value) {

        String sql = baseQuery() + " WHERE " + column + " = ? "
                + "ORDER BY a.account_id";
        List<Account> accounts = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, value);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    accounts.add(mapAccount(resultSet));
                }
            }
            return accounts;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to list accounts", exception);
        }
    }

    private String baseQuery() {

        return "SELECT a.account_id, a.account_type, a.balance, a.status, "
                + "c.customer_id, c.user_id AS customer_user_id, "
                + "u.password_hash, u.full_name, "
                + "br.branch_id, br.branch_name, b.bank_id, b.bank_name "
                + "FROM accounts a "
                + "JOIN customers c ON c.customer_id = a.customer_id "
                + "JOIN users u ON u.user_id = c.user_id "
                + "JOIN branches br ON br.branch_id = a.branch_id "
                + "JOIN banks b ON b.bank_id = br.bank_id";
    }

    private Account mapAccount(ResultSet resultSet) throws SQLException {

        Bank bank = new Bank(
                resultSet.getInt("bank_id"),
                resultSet.getString("bank_name"));
        Branch branch = new Branch(
                resultSet.getInt("branch_id"),
                resultSet.getString("branch_name"),
                bank);
        Customer customer = new Customer(
                resultSet.getInt("customer_id"),
                resultSet.getString("full_name"),
                resultSet.getString("customer_user_id"),
                resultSet.getString("password_hash"),
                branch);

        Account account;
        if ("CURRENT".equalsIgnoreCase(resultSet.getString("account_type"))) {
            account = new CurrentAccount(
                    resultSet.getInt("account_id"),
                    resultSet.getDouble("balance"),
                    customer,
                    branch);
        } else {
            account = new SavingsAccount(
                    resultSet.getInt("account_id"),
                    resultSet.getDouble("balance"),
                    customer,
                    branch);
        }

        account.setStatus(AccountStatus.valueOf(resultSet.getString("status")));
        return account;
    }
}
