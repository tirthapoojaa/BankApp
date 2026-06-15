package service;

import model.Bank;
import repository.BankRepository;

import java.util.List;

public class BankService {

    private BankRepository bankRepository;

    public BankService(
            BankRepository bankRepository) {

        this.bankRepository = bankRepository;
    }

    public void createBank(Bank bank) {

        bankRepository.save(bank);
    }

    public Bank getBank(int id) {

        return bankRepository.findById(id);
    }

    public List<Bank> getAllBanks() {

        return bankRepository.findAll();
    }
}