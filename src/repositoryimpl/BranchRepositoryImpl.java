package repositoryimpl;

import model.Branch;
import repository.BranchRepository;

import java.util.*;

public class BranchRepositoryImpl
        implements BranchRepository {

    private Map<Integer, Branch> branches
            = new HashMap<>();

    @Override
    public void save(Branch branch) {

        branches.put(branch.getBranchId(), branch);
    }

    @Override
    public Branch findById(int id) {

        return branches.get(id);
    }

    @Override
    public List<Branch> findAll() {

        return new ArrayList<>(branches.values());
    }

    @Override
    public void delete(int id) {

        branches.remove(id);
    }
}