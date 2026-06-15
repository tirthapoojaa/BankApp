package repositoryimpl;

import model.Bank;
import repository.BankRepository;

import java.util.*;

public class BankRepositoryImpl
        implements BankRepository {

    private Map<Integer, Bank> banks
            = new HashMap<>();

    @Override
    public void save(Bank bank) {

        banks.put(bank.getBankId(), bank);
    }

    @Override
    public Bank findById(int id) {

        return banks.get(id);
    }

    @Override
    public List<Bank> findAll() {

        return new ArrayList<>(banks.values());
    }

    @Override
    public void delete(int id) {

        banks.remove(id);
    }
}