package repositoryimpl;

import database.DBConnection;
import database.DatabaseInitializer;
import model.Bank;
import repository.BankRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankRepositoryImpl
        implements BankRepository {

    public BankRepositoryImpl() {

        DatabaseInitializer.initialize();
    }

    @Override
    public void save(Bank bank) {

        String sql = "INSERT INTO banks (bank_id, bank_name) "
                + "VALUES (?, ?) "
                + "ON DUPLICATE KEY UPDATE bank_name = VALUES(bank_name)";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bank.getBankId());
            statement.setString(2, bank.getBankName());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to save bank", exception);
        }
    }

    @Override
    public Bank findById(int id) {

        String sql = "SELECT bank_id, bank_name FROM banks WHERE bank_id = ?";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapBank(resultSet);
                }
            }

            return null;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to find bank", exception);
        }
    }

    @Override
    public List<Bank> findAll() {

        String sql = "SELECT bank_id, bank_name FROM banks ORDER BY bank_id";
        List<Bank> banks = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                banks.add(mapBank(resultSet));
            }

            return banks;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to list banks", exception);
        }
    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM banks WHERE bank_id = ?";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to delete bank", exception);
        }
    }

    private Bank mapBank(ResultSet resultSet) throws SQLException {

        return new Bank(
                resultSet.getInt("bank_id"),
                resultSet.getString("bank_name"));
    }
}
