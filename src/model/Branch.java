package model;

import java.util.ArrayList;
import java.util.List;

public class Branch {

    private int branchId;

    private String branchName;

    private Bank bank;

    private List<Customer> customers;

    private List<Employee> employees;

    private List<Account> accounts;

    public Branch() {

        customers = new ArrayList<>();
        employees = new ArrayList<>();
        accounts = new ArrayList<>();
    }

    public Branch(
            int branchId,
            String branchName,
            Bank bank) {

        this();

        this.branchId = branchId;
        this.branchName = branchName;
        this.bank = bank;
    }

    public int getBranchId() {
        return branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public Bank getBank() {
        return bank;
    }
}
