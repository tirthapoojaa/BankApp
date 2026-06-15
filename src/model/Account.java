package model;

import enums.AccountStatus;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    protected int accountId;

    protected double balance;

    protected Customer customer;

    protected Branch branch;

    protected AccountStatus status;

    protected List<Transaction> transactions;

    public Account(
            int accountId,
            double balance,
            Customer customer,
            Branch branch) {

        this.accountId = accountId;
        this.balance = balance;
        this.customer = customer;
        this.branch = branch;

        this.transactions = new ArrayList<>();
    }

    public int getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Branch getBranch() {
        return branch;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public String getAccountType() {
        return getClass().getSimpleName().replace("Account", "");
    }

    @Override
    public String toString() {

        return "Account{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                '}';
    }
}
