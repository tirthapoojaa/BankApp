package controller;

import model.Branch;
import model.Bank;
import service.BranchService;

public class BranchController {

    private BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    public void createBranch(int id, String name, Bank bank) {

        Branch branch = new Branch(id, name, bank);
        branchService.createBranch(branch);

        System.out.println("Branch created successfully");
    }

    public void viewBranch(int id) {

        Branch branch = branchService.getBranch(id);

        if (branch == null) {
            System.out.println("Branch not found");
        } else {
            System.out.println(branch.getBranchName());
        }
    }
}