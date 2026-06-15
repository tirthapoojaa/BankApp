package controller;

import model.Bank;
import service.BankService;

public class BankController {

    private BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    public void createBank(int id, String name) {

        Bank bank = new Bank(id, name);
        bankService.createBank(bank);

        System.out.println("Bank created successfully");
    }

    public void viewBank(int id) {

        Bank bank = bankService.getBank(id);

        if (bank == null) {
            System.out.println("Bank not found");
        } else {
            System.out.println(bank);
        }
    }
}