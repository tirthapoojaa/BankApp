package servlet;

import repositoryimpl.AccountRepositoryImpl;
import repositoryimpl.BankRepositoryImpl;
import repositoryimpl.BranchRepositoryImpl;
import repositoryimpl.CustomerRepositoryImpl;
import repositoryimpl.EmployeeRepositoryImpl;
import repositoryimpl.TransactionRepositoryImpl;
import repositoryimpl.UserRepositoryImpl;
import service.AccountService;
import service.AuthenticationService;
import service.BankService;
import service.BranchService;
import service.CustomerService;
import service.EmployeeService;
import service.TransactionService;
import enums.EmployeeRole;
import model.Bank;
import model.Branch;

final class ApplicationServices {

    static final AuthenticationService AUTHENTICATION =
            new AuthenticationService(new UserRepositoryImpl());

    static final BankService BANK =
            new BankService(new BankRepositoryImpl());

    static final BranchService BRANCH =
            new BranchService(new BranchRepositoryImpl());

    static final CustomerService CUSTOMER =
            new CustomerService(
                    new CustomerRepositoryImpl(),
                    AUTHENTICATION);

    static final EmployeeService EMPLOYEE =
            new EmployeeService(
                    new EmployeeRepositoryImpl(),
                    AUTHENTICATION);

    static final AccountService ACCOUNT =
            new AccountService(new AccountRepositoryImpl());

    static final TransactionService TRANSACTION =
            new TransactionService(new TransactionRepositoryImpl());

    static {
        Bank bank = new Bank(1, "BankingApp Bank");
        BANK.createBank(bank);

        Branch branch = new Branch(1, "Main Branch", bank);
        BRANCH.createBranch(branch);
        bank.addBranch(branch);

        EMPLOYEE.registerEmployee(
                1,
                "System Employee",
                "employee",
                "admin123",
                branch,
                EmployeeRole.BRANCH_MANAGER,
                0.0);
    }

    private ApplicationServices() {
    }
}
