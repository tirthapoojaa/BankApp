package model;

import enums.Role;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

    private static final long serialVersionUID = 1L;

    private int customerId;

    private transient Branch branch;

    private transient List<Account> accounts;

    public Customer(
            int customerId,
            String fullName,
            String userId,
            String passwordHash,
            Branch branch) {

        super(userId, passwordHash, fullName, Role.CUSTOMER);

        this.customerId = customerId;
        this.branch = branch;

        this.accounts = new ArrayList<>();
    }

    public int getCustomerId() {
        return customerId;
    }

    public Branch getBranch() {
        return branch;
    }

    public List<Account> getAccounts() {
        if (accounts == null) {
            accounts = new ArrayList<>();
        }
        return accounts;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public void addAccount(Account account) {

        getAccounts().add(account);
    }

    @Override
    public String toString() {

        return "Customer{" +
                "customerId=" + customerId +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
