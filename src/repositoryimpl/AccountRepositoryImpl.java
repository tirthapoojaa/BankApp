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
    public List<Account> findAll() {

        return new ArrayList<>(accounts.values());
    }

    @Override
    public void delete(int id) {

        accounts.remove(id);
    }
}