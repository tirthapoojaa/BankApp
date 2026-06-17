package service;

import model.Account;
import repository.AccountRepository;

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

    public List<Account> getAccountsByCustomerId(int customerId) {

        return accountRepository.findByCustomerId(customerId);
    }

    public List<Account> getAccountsByBranchId(int branchId) {

        return accountRepository.findByBranchId(branchId);
    }

    public void deposit(
            int accountId,
            double amount) {

        Account account =
                accountRepository.findById(accountId);

        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        account.setBalance(
                account.getBalance() + amount);
        accountRepository.save(account);
    }

    public void withdraw(
            int accountId,
            double amount) {

        Account account =
                accountRepository.findById(accountId);

        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        account.setBalance(
                account.getBalance() - amount);
        accountRepository.save(account);
    }
}
