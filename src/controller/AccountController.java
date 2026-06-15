package controller;

import model.Customer;
import model.Branch;
import model.Account;
import service.AccountService;

public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public void createAccount(Account account) {

        accountService.createAccount(account);

        System.out.println("Account created successfully");
    }

    public void deposit(int accountId, double amount) {

        accountService.deposit(accountId, amount);

        System.out.println("Deposit successful");
    }

    public void withdraw(int accountId, double amount) {

        accountService.withdraw(accountId, amount);

        System.out.println("Withdraw successful");
    }

    public void viewAccount(int id) {

        Account account = accountService.getAccount(id);

        if (account == null) {
            System.out.println("Account not found");
        } else {
            System.out.println("Balance: " + account.getBalance());
        }
    }
}