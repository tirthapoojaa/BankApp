package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseInitializer {

    private static boolean initialized;

    private DatabaseInitializer() {
    }

    public static synchronized void initialize() {

        if (initialized) {
            return;
        }

        try (Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement()) {

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users ("
                    + "user_id VARCHAR(50) PRIMARY KEY, "
                    + "password_hash VARCHAR(255) NOT NULL, "
                    + "full_name VARCHAR(100) NOT NULL, "
                    + "role VARCHAR(20) NOT NULL"
                    + ")");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS banks ("
                    + "bank_id INT PRIMARY KEY, "
                    + "bank_name VARCHAR(100) NOT NULL"
                    + ")");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS branches ("
                    + "branch_id INT PRIMARY KEY, "
                    + "branch_name VARCHAR(100) NOT NULL, "
                    + "bank_id INT NOT NULL, "
                    + "FOREIGN KEY (bank_id) REFERENCES banks(bank_id)"
                    + ")");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS customers ("
                    + "customer_id INT PRIMARY KEY, "
                    + "user_id VARCHAR(50) NOT NULL UNIQUE, "
                    + "branch_id INT NULL, "
                    + "FOREIGN KEY (user_id) REFERENCES users(user_id), "
                    + "FOREIGN KEY (branch_id) REFERENCES branches(branch_id)"
                    + ")");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS employees ("
                    + "employee_id INT PRIMARY KEY, "
                    + "user_id VARCHAR(50) NOT NULL UNIQUE, "
                    + "branch_id INT NOT NULL, "
                    + "employee_role VARCHAR(40) NOT NULL, "
                    + "salary DECIMAL(12,2) NOT NULL DEFAULT 0.00, "
                    + "FOREIGN KEY (user_id) REFERENCES users(user_id), "
                    + "FOREIGN KEY (branch_id) REFERENCES branches(branch_id)"
                    + ")");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS accounts ("
                    + "account_id INT PRIMARY KEY, "
                    + "customer_id INT NOT NULL, "
                    + "branch_id INT NOT NULL, "
                    + "account_type VARCHAR(20) NOT NULL, "
                    + "balance DECIMAL(15,2) NOT NULL DEFAULT 0.00, "
                    + "status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE', "
                    + "FOREIGN KEY (customer_id) REFERENCES customers(customer_id), "
                    + "FOREIGN KEY (branch_id) REFERENCES branches(branch_id)"
                    + ")");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS transactions ("
                    + "transaction_id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "amount DECIMAL(15,2) NOT NULL, "
                    + "type VARCHAR(20) NOT NULL, "
                    + "status VARCHAR(20) NOT NULL, "
                    + "from_account_id INT NULL, "
                    + "to_account_id INT NULL, "
                    + "created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                    + "FOREIGN KEY (from_account_id) REFERENCES accounts(account_id), "
                    + "FOREIGN KEY (to_account_id) REFERENCES accounts(account_id)"
                    + ")");
            statement.executeUpdate("ALTER TABLE transactions "
                    + "MODIFY transaction_id INT NOT NULL AUTO_INCREMENT");

            initialized = true;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to initialize database schema", exception);
        }
    }
}
