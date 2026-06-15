package main;

import controller.*;
import repositoryimpl.*;
import service.*;

import model.*;
import enums.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // =========================
        // REPOSITORIES
        // =========================
        BankRepositoryImpl bankRepo = new BankRepositoryImpl();
        BranchRepositoryImpl branchRepo = new BranchRepositoryImpl();
        CustomerRepositoryImpl customerRepo = new CustomerRepositoryImpl();
        AccountRepositoryImpl accountRepo = new AccountRepositoryImpl();
        TransactionRepositoryImpl transactionRepo = new TransactionRepositoryImpl();
        UserRepositoryImpl userRepo = new UserRepositoryImpl();

        // =========================
        // SERVICES
        // =========================
        BankService bankService = new BankService(bankRepo);
        BranchService branchService = new BranchService(branchRepo);
        AuthenticationService authenticationService = new AuthenticationService(userRepo);
        CustomerService customerService = new CustomerService(
                customerRepo, authenticationService);
        AccountService accountService = new AccountService(accountRepo);
        TransactionService transactionService = new TransactionService(transactionRepo);

        // =========================
        // CONTROLLERS
        // =========================
        BankController bankController = new BankController(bankService);
        BranchController branchController = new BranchController(branchService);
        CustomerController customerController = new CustomerController(customerService);
        AccountController accountController = new AccountController(accountService);
        TransactionController transactionController = new TransactionController(transactionService);

        while (true) {

            System.out.println("\n====== BANK SYSTEM ======");
            System.out.println("1. Create Bank");
            System.out.println("2. View Bank");
            System.out.println("3. Create Branch");
            System.out.println("4. Create Customer");
            System.out.println("5. Create Account");
            System.out.println("6. Deposit");
            System.out.println("7. Withdraw");
            System.out.println("8. View Account");
            System.out.println("9. View Transactions");
            System.out.println("0. Exit");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("Enter Bank ID:");
                    int bid = sc.nextInt();

                    System.out.println("Enter Bank Name:");
                    String bname = sc.next();

                    bankController.createBank(bid, bname);
                    break;

                case 2:
                    System.out.println("Enter Bank ID:");
                    bankController.viewBank(sc.nextInt());
                    break;

                case 3:
                    System.out.println("Enter Branch ID:");
                    int brId = sc.nextInt();

                    System.out.println("Enter Branch Name:");
                    String brName = sc.next();

                    System.out.println("Enter Bank ID:");
                    int bankId = sc.nextInt();

                    Bank bank = bankService.getBank(bankId);

                    branchController.createBranch(brId, brName, bank);
                    break;

                case 4:
                    System.out.println("Enter Customer ID:");
                    int cid = sc.nextInt();

                    System.out.println("Enter Name:");
                    String cname = sc.next();

                    System.out.println("Enter Username:");
                    String uname = sc.next();

                    System.out.println("Enter Password:");
                    String pass = sc.next();

                    System.out.println("Enter Branch ID:");
                    int branchId = sc.nextInt();

                    Branch branch = branchService.getBranch(branchId);

                    customerController.createCustomer(
                            cid, cname, uname, pass, branch);
                    break;

                case 5:
                    System.out.println("Enter Account ID:");
                    int accId = sc.nextInt();

                    System.out.println("Enter Customer ID:");
                    int custId = sc.nextInt();

                    Customer customer = customerService.getCustomer(custId);

                    Account account =
                            new SavingsAccount(
                                    accId,
                                    0.0,
                                    customer,
                                    customer.getBranch()
                            );

                    accountController.createAccount(account);
                    break;

                case 6:
                    System.out.println("Enter Account ID:");
                    int depId = sc.nextInt();

                    System.out.println("Enter Amount:");
                    double depAmt = sc.nextDouble();

                    accountController.deposit(depId, depAmt);
                    break;

                case 7:
                    System.out.println("Enter Account ID:");
                    int wId = sc.nextInt();

                    System.out.println("Enter Amount:");
                    double wAmt = sc.nextDouble();

                    accountController.withdraw(wId, wAmt);
                    break;

                case 8:
                    System.out.println("Enter Account ID:");
                    accountController.viewAccount(sc.nextInt());
                    break;

                case 9:
                    transactionController.viewAllTransactions();
                    break;

                case 0:
                    System.out.println("Exiting...");
                    return;
            }
        }
    }
}
