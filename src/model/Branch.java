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

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addCustomer(Customer customer) {
        if (!customers.contains(customer)) {
            customers.add(customer);
        }
    }

    public void addEmployee(Employee employee) {
        if (!employees.contains(employee)) {
            employees.add(employee);
        }
    }

    public void addAccount(Account account) {
        if (!accounts.contains(account)) {
            accounts.add(account);
        }
    }
}
