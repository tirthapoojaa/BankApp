package service;

import model.Transaction;
import repository.TransactionRepository;

import java.util.List;

public class TransactionService {

    private TransactionRepository
            transactionRepository;

    public TransactionService(
            TransactionRepository transactionRepository) {

        this.transactionRepository =
                transactionRepository;
    }

    public void saveTransaction(
            Transaction transaction) {

        transactionRepository.save(transaction);
    }

    public Transaction getTransaction(int id) {

        return transactionRepository.findById(id);
    }

    public List<Transaction> getAllTransactions() {

        return transactionRepository.findAll();
    }
}