package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import service.*;
import model.*;

public class TransactionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TransactionService transactionService;
    private AccountService accountService;

    public void init() throws ServletException {
        transactionService = ApplicationServices.TRANSACTION;
        accountService = ApplicationServices.ACCOUNT;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");

        if ("deposit".equals(action)) {
            try {
                int accountId = Integer.parseInt(request.getParameter("accountId"));
                double amount = Double.parseDouble(request.getParameter("amount"));

                Account account = accountService.getAccount(accountId);
                if (account == null) {
                    out.println("{\"status\": \"error\", \"message\": \"Account not found\"}");
                    return;
                }

                account.setBalance(account.getBalance() + amount);
                out.println("{\"status\": \"success\", \"message\": \"Deposit successful\", \"newBalance\": " + account.getBalance() + "}");
            } catch (Exception e) {
                out.println("{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}");
            }
        } else if ("withdraw".equals(action)) {
            try {
                int accountId = Integer.parseInt(request.getParameter("accountId"));
                double amount = Double.parseDouble(request.getParameter("amount"));

                Account account = accountService.getAccount(accountId);
                if (account == null) {
                    out.println("{\"status\": \"error\", \"message\": \"Account not found\"}");
                    return;
                }

                if (account.getBalance() < amount) {
                    out.println("{\"status\": \"error\", \"message\": \"Insufficient balance\"}");
                    return;
                }

                account.setBalance(account.getBalance() - amount);
                out.println("{\"status\": \"success\", \"message\": \"Withdrawal successful\", \"newBalance\": " + account.getBalance() + "}");
            } catch (Exception e) {
                out.println("{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");

        if ("view".equals(action)) {
            try {
                int accountId = Integer.parseInt(request.getParameter("accountId"));
                out.println("{\"status\": \"success\", \"message\": \"View transactions for account: " + accountId + "\"}");
            } catch (Exception e) {
                out.println("{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}");
            }
        }
    }
}
