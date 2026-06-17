package repositoryimpl;

import database.DBConnection;
import database.DatabaseInitializer;
import model.Bank;
import model.Branch;
import repository.BranchRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BranchRepositoryImpl
        implements BranchRepository {

    public BranchRepositoryImpl() {

        DatabaseInitializer.initialize();
    }

    @Override
    public void save(Branch branch) {

        String sql = "INSERT INTO branches (branch_id, branch_name, bank_id) "
                + "VALUES (?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE branch_name = VALUES(branch_name), "
                + "bank_id = VALUES(bank_id)";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, branch.getBranchId());
            statement.setString(2, branch.getBranchName());
            statement.setInt(3, branch.getBank().getBankId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to save branch", exception);
        }
    }

    @Override
    public Branch findById(int id) {

        String sql = "SELECT br.branch_id, br.branch_name, "
                + "b.bank_id, b.bank_name "
                + "FROM branches br "
                + "JOIN banks b ON b.bank_id = br.bank_id "
                + "WHERE br.branch_id = ?";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapBranch(resultSet);
                }
            }
            return null;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to find branch", exception);
        }
    }

    @Override
    public List<Branch> findAll() {

        String sql = "SELECT br.branch_id, br.branch_name, "
                + "b.bank_id, b.bank_name "
                + "FROM branches br "
                + "JOIN banks b ON b.bank_id = br.bank_id "
                + "ORDER BY br.branch_id";
        List<Branch> branches = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                branches.add(mapBranch(resultSet));
            }
            return branches;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to list branches", exception);
        }
    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM branches WHERE branch_id = ?";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to delete branch", exception);
        }
    }

    private Branch mapBranch(ResultSet resultSet) throws SQLException {

        Bank bank = new Bank(
                resultSet.getInt("bank_id"),
                resultSet.getString("bank_name"));
        return new Branch(
                resultSet.getInt("branch_id"),
                resultSet.getString("branch_name"),
                bank);
    }
}
