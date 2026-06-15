package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import service.*;
import model.*;

public class BankServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BankService bankService;

    public void init() throws ServletException {
        bankService = ApplicationServices.BANK;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");

        if ("create".equals(action)) {
            try {
                int bankId = Integer.parseInt(request.getParameter("bankId"));
                String bankName = request.getParameter("bankName");

                Bank bank = new Bank(bankId, bankName);
                bankService.createBank(bank);

                out.println("{\"status\": \"success\", \"message\": \"Bank created successfully\"}");
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
                int bankId = Integer.parseInt(request.getParameter("bankId"));
                Bank bank = bankService.getBank(bankId);

                if (bank != null) {
                    out.println("{\"status\": \"success\", \"bankId\": " + bank.getBankId()
                            + ", \"bankName\": \"" + bank.getBankName() + "\"}");
                } else {
                    out.println("{\"status\": \"error\", \"message\": \"Bank not found\"}");
                }
            } catch (Exception e) {
                out.println("{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}");
            }
        }
    }
}
