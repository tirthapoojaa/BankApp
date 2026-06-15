package model;

public class SavingsAccount extends Account {

    public SavingsAccount(
            int accountId,
            double balance,
            Customer customer,
            Branch branch) {

        super(accountId, balance, customer, branch);
    }
}