package repositoryimpl;

import model.Transaction;
import repository.TransactionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionRepositoryImpl
        implements TransactionRepository {

    private Map<Integer, Transaction> transactions
            = new HashMap<>();

    @Override
    public void save(Transaction transaction) {

        transactions.put(
                transaction.getTransactionId(),
                transaction);
    }

    @Override
    public Transaction findById(int id) {

        return transactions.get(id);
    }

    @Override
    public List<Transaction> findAll() {

        return new ArrayList<>(
                transactions.values());
    }

    @Override
    public void delete(int id) {

        transactions.remove(id);
    }
}