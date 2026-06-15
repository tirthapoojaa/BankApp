package repository;

import model.Branch;

import java.util.List;

public interface BranchRepository {

    void save(Branch branch);

    Branch findById(int id);

    List<Branch> findAll();

    void delete(int id);
}