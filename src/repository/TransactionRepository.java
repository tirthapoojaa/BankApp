package repository;

import model.Transaction;

import java.util.List;

public interface TransactionRepository {

    void save(Transaction transaction);

    Transaction findById(int id);

    List<Transaction> findByAccountId(int accountId);

    List<Transaction> findAll();

    void delete(int id);
}
