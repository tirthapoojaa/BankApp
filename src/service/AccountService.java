package service;

import model.Account;
import model.Transaction;

import repository.AccountRepository;

import enums.TransactionStatus;
import enums.TransactionType;

import java.util.List;

public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(
            AccountRepository accountRepository) {

        this.accountRepository = accountRepository;
    }

    public void createAccount(Account account) {

        accountRepository.save(account);
    }

    public Account getAccount(int id) {

        return accountRepository.findById(id);
    }

    public List<Account> getAllAccounts() {

        return accountRepository.findAll();
    }

    public void deposit(
            int accountId,
            double amount) {

        Account account =
                accountRepository.findById(accountId);

        account.setBalance(
                account.getBalance() + amount);

        Transaction transaction =
                new Transaction(
                        1,
                        amount,
                        TransactionType.DEPOSIT,
                        TransactionStatus.SUCCESS,
                        account,
                        account
                );

        account.getTransactions()
                .add(transaction);
    }

    public void withdraw(
            int accountId,
            double amount) {

        Account account =
                accountRepository.findById(accountId);

        if(account.getBalance() >= amount) {

            account.setBalance(
                    account.getBalance() - amount);

            Transaction transaction =
                    new Transaction(
                            2,
                            amount,
                            TransactionType.WITHDRAW,
                            TransactionStatus.SUCCESS,
                            account,
                            account
                    );

            account.getTransactions()
                    .add(transaction);
        }
    }
}