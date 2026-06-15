package repository;

import model.Account;

import java.util.List;

public interface AccountRepository {

    void save(Account account);

    Account findById(int id);

    List<Account> findByCustomerId(int customerId);

    List<Account> findByBranchId(int branchId);

    List<Account> findAll();

    void delete(int id);
}
