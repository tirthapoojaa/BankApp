package model;

public class CurrentAccount extends Account {

    public CurrentAccount(
            int accountId,
            double balance,
            Customer customer,
            Branch branch) {

        super(accountId, balance, customer, branch);
    }
}