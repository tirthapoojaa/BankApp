package repositoryimpl;

import model.Account;
import repository.AccountRepository;

import java.util.*;

public class AccountRepositoryImpl
        implements AccountRepository {

    private Map<Integer, Account> accounts
            = new HashMap<>();

    @Override
    public void save(Account account) {

        accounts.put(account.getAccountId(), account);
    }

    @Override
    public Account findById(int id) {

        return accounts.get(id);
    }

    @Override
    public List<Account> findByCustomerId(int customerId) {

        List<Account> matches = new ArrayList<>();
        for (Account account : accounts.values()) {
            if (account.getCustomer() != null
                    && account.getCustomer().getCustomerId() == customerId) {
                matches.add(account);
            }
        }
        return matches;
    }

    @Override
    public List<Account> findByBranchId(int branchId) {

        List<Account> matches = new ArrayList<>();
        for (Account account : accounts.values()) {
            if (account.getBranch() != null
                    && account.getBranch().getBranchId() == branchId) {
                matches.add(account);
            }
        }
        return matches;
    }

    @Override
    public List<Account> findAll() {

        return new ArrayList<>(accounts.values());
    }

    @Override
    public void delete(int id) {

        accounts.remove(id);
    }
}
