package servlet;

import enums.TransactionStatus;
import enums.TransactionType;
import model.Account;
import model.Transaction;
import model.User;
import service.AccountService;
import service.TransactionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final AtomicInteger TRANSACTION_IDS = new AtomicInteger(1);

    private TransactionService transactionService;
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        transactionService = ApplicationServices.TRANSACTION;
        accountService = ApplicationServices.ACCOUNT;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            double amount = Double.parseDouble(request.getParameter("amount"));
            Account account = accountService.getAccount(accountId);

            if (account == null) {
                writeError(out, "Account not found");
                return;
            }
            if (!ownsAccount(request, account)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                writeError(out, "Account access denied");
                return;
            }
            if (amount <= 0) {
                writeError(out, "Amount must be greater than zero");
                return;
            }

            String action = request.getParameter("action");
            TransactionType type;

            if ("deposit".equals(action)) {
                accountService.deposit(accountId, amount);
                type = TransactionType.DEPOSIT;
            } else if ("withdraw".equals(action)) {
                accountService.withdraw(accountId, amount);
                type = TransactionType.WITHDRAW;
            } else {
                writeError(out, "Invalid action");
                return;
            }

            Transaction transaction = new Transaction(
                    TRANSACTION_IDS.getAndIncrement(),
                    amount,
                    type,
                    TransactionStatus.SUCCESS,
                    account,
                    account);
            transactionService.saveTransaction(transaction);
            account.getTransactions().add(transaction);

            out.println("{\"status\":\"success\",\"message\":\""
                    + (type == TransactionType.DEPOSIT
                    ? "Deposit successful" : "Withdrawal successful")
                    + "\",\"newBalance\":" + account.getBalance() + "}");
        } catch (Exception exception) {
            writeError(out, exception.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            Account account = accountService.getAccount(accountId);

            if (account == null) {
                writeError(out, "Account not found");
                return;
            }
            if (!ownsAccount(request, account)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                writeError(out, "Account access denied");
                return;
            }

            List<Transaction> transactions =
                    transactionService.getTransactionsByAccountId(accountId);
            StringBuilder json = new StringBuilder(
                    "{\"status\":\"success\",\"transactions\":[");

            for (int index = 0; index < transactions.size(); index++) {
                if (index > 0) {
                    json.append(',');
                }
                Transaction transaction = transactions.get(index);
                json.append("{\"transactionId\":")
                        .append(transaction.getTransactionId())
                        .append(",\"type\":\"")
                        .append(transaction.getType())
                        .append("\",\"amount\":")
                        .append(transaction.getAmount())
                        .append(",\"transactionStatus\":\"")
                        .append(transaction.getStatus())
                        .append("\"}");
            }

            json.append("]}");
            out.println(json);
        } catch (Exception exception) {
            writeError(out, exception.getMessage());
        }
    }

    private boolean ownsAccount(HttpServletRequest request, Account account) {
        User user = (User) request.getSession().getAttribute("user");
        return account.getCustomer() != null
                && account.getCustomer().getUserId().equals(user.getUserId());
    }

    private void writeError(PrintWriter out, String message) {
        out.println("{\"status\":\"error\",\"message\":\""
                + (message == null ? "Request failed" : message) + "\"}");
    }
}
