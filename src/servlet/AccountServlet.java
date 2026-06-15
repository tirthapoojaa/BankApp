package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import service.*;
import model.*;

public class AccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AccountService accountService;
    private CustomerService customerService;

    public void init() throws ServletException {
        accountService = ApplicationServices.ACCOUNT;
        customerService = ApplicationServices.CUSTOMER;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");

        if ("create".equals(action)) {
            try {
                int accountId = Integer.parseInt(request.getParameter("accountId"));
                int customerId = Integer.parseInt(request.getParameter("customerId"));
                double balance = Double.parseDouble(request.getParameter("balance"));
                String accountType = request.getParameter("accountType");

                Customer customer = customerService.getCustomer(customerId);
                if (customer == null) {
                    out.println("{\"status\": \"error\", \"message\": \"Customer not found\"}");
                    return;
                }

                Account account;
                if ("savings".equalsIgnoreCase(accountType)) {
                    account = new SavingsAccount(accountId, balance, customer, null);
                } else {
                    account = new CurrentAccount(accountId, balance, customer, null);
                }

                accountService.createAccount(account);
                out.println("{\"status\": \"success\", \"message\": \"Account created successfully\"}");
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
                Account account = accountService.getAccount(accountId);

                if (account != null) {
                    out.println("{\"status\": \"success\", \"accountId\": " + account.getAccountId()
                            + ", \"balance\": " + account.getBalance() + "}");
                } else {
                    out.println("{\"status\": \"error\", \"message\": \"Account not found\"}");
                }
            } catch (Exception e) {
                out.println("{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}");
            }
        }
    }
}
