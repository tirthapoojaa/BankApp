package model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

    private Branch branch;

    private List<Account> accounts;

    public Customer(
            int userId,
            String name,
            String username,
            String password,
            Branch branch) {

        super(userId, name, username, password);

        this.branch = branch;

        this.accounts = new ArrayList<>();
    }

    public Branch getBranch() {
        return branch;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public void addAccount(Account account) {

        accounts.add(account);
    }

    @Override
    public String toString() {

        return "Customer{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                '}';
    }
}