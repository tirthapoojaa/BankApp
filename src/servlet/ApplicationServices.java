package servlet;

import repositoryimpl.AccountRepositoryImpl;
import repositoryimpl.BankRepositoryImpl;
import repositoryimpl.BranchRepositoryImpl;
import repositoryimpl.CustomerRepositoryImpl;
import repositoryimpl.TransactionRepositoryImpl;
import service.AccountService;
import service.BankService;
import service.BranchService;
import service.CustomerService;
import service.TransactionService;

final class ApplicationServices {

    static final BankService BANK =
            new BankService(new BankRepositoryImpl());

    static final BranchService BRANCH =
            new BranchService(new BranchRepositoryImpl());

    static final CustomerService CUSTOMER =
            new CustomerService(new CustomerRepositoryImpl());

    static final AccountService ACCOUNT =
            new AccountService(new AccountRepositoryImpl());

    static final TransactionService TRANSACTION =
            new TransactionService(new TransactionRepositoryImpl());

    private ApplicationServices() {
    }
}
