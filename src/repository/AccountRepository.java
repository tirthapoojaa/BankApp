package repository;

import model.Account;

import java.util.List;

public interface AccountRepository {

    void save(Account account);

    Account findById(int id);

    List<Account> findAll();

    void delete(int id);
}