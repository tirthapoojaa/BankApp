package service;

import model.Branch;
import repository.BranchRepository;

import java.util.List;

public class BranchService {

    private BranchRepository branchRepository;

    public BranchService(
            BranchRepository branchRepository) {

        this.branchRepository = branchRepository;
    }

    public void createBranch(Branch branch) {

        branchRepository.save(branch);
    }

    public Branch getBranch(int id) {

        return branchRepository.findById(id);
    }

    public List<Branch> getAllBranches() {

        return branchRepository.findAll();
    }
}