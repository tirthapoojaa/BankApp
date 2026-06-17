package repositoryimpl;

import database.DBConnection;
import database.DatabaseInitializer;
import enums.TransactionStatus;
import enums.TransactionType;
import model.Account;
import model.Transaction;
import repository.TransactionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepositoryImpl
        implements TransactionRepository {

    private final AccountRepositoryImpl accountRepository =
            new AccountRepositoryImpl();

    public TransactionRepositoryImpl() {

        DatabaseInitializer.initialize();
    }

    @Override
    public void save(Transaction transaction) {

        String sql = "INSERT INTO transactions "
                + "(amount, type, status, from_account_id, to_account_id) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, transaction.getAmount());
            statement.setString(2, transaction.getType().name());
            statement.setString(3, transaction.getStatus().name());
            statement.setInt(4, transaction.getFromAccount().getAccountId());
            statement.setInt(5, transaction.getToAccount().getAccountId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to save transaction", exception);
        }
    }

    @Override
    public Transaction findById(int id) {

        String sql = "SELECT transaction_id, amount, type, status, "
                + "from_account_id, to_account_id "
                + "FROM transactions WHERE transaction_id = ?";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapTransaction(resultSet);
                }
            }
            return null;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to find transaction", exception);
        }
    }

    @Override
    public List<Transaction> findByAccountId(int accountId) {

        String sql = "SELECT transaction_id, amount, type, status, "
                + "from_account_id, to_account_id "
                + "FROM transactions "
                + "WHERE from_account_id = ? OR to_account_id = ? "
                + "ORDER BY transaction_id";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, accountId);
            statement.setInt(2, accountId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    transactions.add(mapTransaction(resultSet));
                }
            }
            return transactions;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to list transactions", exception);
        }
    }

    @Override
    public List<Transaction> findAll() {

        String sql = "SELECT transaction_id, amount, type, status, "
                + "from_account_id, to_account_id "
                + "FROM transactions ORDER BY transaction_id";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                transactions.add(mapTransaction(resultSet));
            }
            return transactions;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to list transactions", exception);
        }
    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM transactions WHERE transaction_id = ?";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to delete transaction", exception);
        }
    }

    private Transaction mapTransaction(ResultSet resultSet) throws SQLException {

        Account fromAccount = accountRepository.findById(
                resultSet.getInt("from_account_id"));
        Account toAccount = accountRepository.findById(
                resultSet.getInt("to_account_id"));

        return new Transaction(
                resultSet.getInt("transaction_id"),
                resultSet.getDouble("amount"),
                TransactionType.valueOf(resultSet.getString("type")),
                TransactionStatus.valueOf(resultSet.getString("status")),
                fromAccount,
                toAccount);
    }
}
