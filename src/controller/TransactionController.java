package controller;

import service.TransactionService;
import model.Transaction;

import java.util.List;

public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void viewTransaction(int id) {

        Transaction t = transactionService.getTransaction(id);

        if (t == null) {
            System.out.println("Transaction not found");
        } else {
            System.out.println(t);
        }
    }

    public void viewAllTransactions() {

        List<Transaction> list =
                transactionService.getAllTransactions();

        for (Transaction t : list) {
            System.out.println(t);
        }
    }
}