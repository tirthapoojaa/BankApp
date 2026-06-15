package repository;

import model.Bank;

import java.util.List;

public interface BankRepository {

    void save(Bank bank);

    Bank findById(int id);

    List<Bank> findAll();

    void delete(int id);
}