package model;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    private int bankId;

    private String bankName;

    private List<Branch> branches;

    public Bank() {

        branches = new ArrayList<>();
    }

    public Bank(int bankId, String bankName) {

        this.bankId = bankId;
        this.bankName = bankName;
        this.branches = new ArrayList<>();
    }

    public int getBankId() {
        return bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void addBranch(Branch branch) {

        branches.add(branch);
    }
}