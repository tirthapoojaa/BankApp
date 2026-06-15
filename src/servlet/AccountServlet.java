package servlet;

import enums.AccountStatus;
import enums.Role;
import model.Account;
import model.Branch;
import model.CurrentAccount;
import model.Customer;
import model.Employee;
import model.SavingsAccount;
import model.User;
import service.AccountService;
import service.BranchService;
import service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AccountServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private AccountService accountService;
    private CustomerService customerService;
    private BranchService branchService;

    @Override
    public void init() throws ServletException {
        accountService = ApplicationServices.ACCOUNT;
        customerService = ApplicationServices.CUSTOMER;
        branchService = ApplicationServices.BRANCH;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (!"create".equals(request.getParameter("action"))) {
            writeError(out, "Invalid action");
            return;
        }

        try {
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            double balance = Double.parseDouble(request.getParameter("balance"));
            String accountType = request.getParameter("accountType");
            User user = getUser(request);

            if (accountService.getAccount(accountId) != null) {
                writeError(out, "Account ID already exists");
                return;
            }
            if (balance < 0) {
                writeError(out, "Initial balance cannot be negative");
                return;
            }

            Customer customer;
            Branch branch;

            if (user.getRole() == Role.CUSTOMER) {
                customer = (Customer) user;
                branch = resolveCustomerBranch(request, customer);
            } else {
                Employee employee = (Employee) user;
                branch = employee.getBranch();
                int customerId = Integer.parseInt(request.getParameter("customerId"));
                customer = customerService.getCustomer(customerId);

                if (customer == null) {
                    writeError(out, "Customer not found");
                    return;
                }
                if (customer.getBranch() != null
                        && customer.getBranch().getBranchId() != branch.getBranchId()) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    writeError(out, "Customer belongs to another branch");
                    return;
                }
                assignCustomerToBranch(customer, branch);
            }

            Account account = createAccount(
                    accountId, balance, customer, branch, accountType);
            account.setStatus(AccountStatus.ACTIVE);
            accountService.createAccount(account);
            customer.addAccount(account);
            branch.addAccount(account);

            out.println("{\"status\": \"success\", \"message\": "
                    + "\"Account created successfully\", \"accountId\": "
                    + account.getAccountId() + "}");
        } catch (Exception exception) {
            writeError(out, exception.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        User user = getUser(request);

        try {
            if ("myAccounts".equals(action)) {
                if (user.getRole() != Role.CUSTOMER) {
                    deny(response, out);
                    return;
                }
                Customer customer = (Customer) user;
                writeAccounts(out,
                        accountService.getAccountsByCustomerId(customer.getCustomerId()));
                return;
            }

            if ("branchAccounts".equals(action)) {
                if (user.getRole() != Role.EMPLOYEE) {
                    deny(response, out);
                    return;
                }
                Employee employee = (Employee) user;
                writeAccounts(out, accountService.getAccountsByBranchId(
                        employee.getBranch().getBranchId()));
                return;
            }

            if ("view".equals(action) || "search".equals(action)) {
                int accountId = Integer.parseInt(request.getParameter("accountId"));
                Account account = accountService.getAccount(accountId);

                if (account == null) {
                    writeError(out, "Account not found");
                    return;
                }
                if (!canAccess(user, account)) {
                    deny(response, out);
                    return;
                }
                writeAccount(out, account);
                return;
            }

            writeError(out, "Invalid action");
        } catch (Exception exception) {
            writeError(out, exception.getMessage());
        }
    }

    private Branch resolveCustomerBranch(
            HttpServletRequest request,
            Customer customer) {

        String branchIdValue = request.getParameter("branchId");
        if (branchIdValue == null || branchIdValue.isEmpty()) {
            throw new IllegalArgumentException("Branch ID is required");
        }

        Branch branch = branchService.getBranch(Integer.parseInt(branchIdValue));
        if (branch == null) {
            throw new IllegalArgumentException("Branch not found");
        }
        if (customer.getBranch() != null
                && customer.getBranch().getBranchId() != branch.getBranchId()) {
            throw new IllegalArgumentException(
                    "New accounts must use your existing branch");
        }

        assignCustomerToBranch(customer, branch);
        return branch;
    }

    private void assignCustomerToBranch(Customer customer, Branch branch) {
        if (customer.getBranch() == null) {
            customer.setBranch(branch);
        }
        branch.addCustomer(customer);
    }

    private Account createAccount(
            int accountId,
            double balance,
            Customer customer,
            Branch branch,
            String accountType) {

        if ("savings".equalsIgnoreCase(accountType)) {
            return new SavingsAccount(accountId, balance, customer, branch);
        }
        if ("current".equalsIgnoreCase(accountType)) {
            return new CurrentAccount(accountId, balance, customer, branch);
        }
        throw new IllegalArgumentException("Unsupported account type");
    }

    private boolean canAccess(User user, Account account) {
        if (user.getRole() == Role.CUSTOMER) {
            return account.getCustomer() != null
                    && account.getCustomer().getUserId().equals(user.getUserId());
        }

        Employee employee = (Employee) user;
        return account.getBranch() != null
                && account.getBranch().getBranchId()
                == employee.getBranch().getBranchId();
    }

    private User getUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }

    private void writeAccounts(PrintWriter out, List<Account> accounts) {
        StringBuilder json = new StringBuilder(
                "{\"status\":\"success\",\"accounts\":[");

        for (int index = 0; index < accounts.size(); index++) {
            if (index > 0) {
                json.append(',');
            }
            json.append(accountJson(accounts.get(index)));
        }

        json.append("]}");
        out.println(json);
    }

    private void writeAccount(PrintWriter out, Account account) {
        out.println("{\"status\":\"success\",\"account\":"
                + accountJson(account) + "}");
    }

    private String accountJson(Account account) {
        return "{\"accountId\":" + account.getAccountId()
                + ",\"customerId\":" + account.getCustomer().getCustomerId()
                + ",\"customerName\":\"" + account.getCustomer().getFullName()
                + "\",\"branchId\":" + account.getBranch().getBranchId()
                + ",\"branchName\":\"" + account.getBranch().getBranchName()
                + "\",\"accountType\":\"" + account.getAccountType()
                + "\",\"balance\":" + account.getBalance()
                + ",\"accountStatus\":\"" + account.getStatus() + "\"}";
    }

    private void deny(HttpServletResponse response, PrintWriter out) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        writeError(out, "Account access denied");
    }

    private void writeError(PrintWriter out, String message) {
        out.println("{\"status\":\"error\",\"message\":\""
                + (message == null ? "Request failed" : message) + "\"}");
    }
}
