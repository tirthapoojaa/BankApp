package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import service.*;
import model.*;

public class BranchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BranchService branchService;
    private BankService bankService;

    public void init() throws ServletException {
        branchService = ApplicationServices.BRANCH;
        bankService = ApplicationServices.BANK;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if ("list".equals(request.getParameter("action"))) {
            StringBuilder json = new StringBuilder(
                    "{\"status\":\"success\",\"branches\":[");
            java.util.List<Branch> branches = branchService.getAllBranches();

            for (int index = 0; index < branches.size(); index++) {
                if (index > 0) {
                    json.append(',');
                }
                Branch branch = branches.get(index);
                json.append("{\"branchId\":")
                        .append(branch.getBranchId())
                        .append(",\"branchName\":\"")
                        .append(branch.getBranchName())
                        .append("\",\"bankName\":\"")
                        .append(branch.getBank().getBankName())
                        .append("\"}");
            }

            json.append("]}");
            out.println(json);
            return;
        }

        if (!"view".equals(request.getParameter("action"))) {
            out.println("{\"status\": \"error\", \"message\": \"Invalid action\"}");
            return;
        }

        try {
            int branchId = Integer.parseInt(request.getParameter("branchId"));
            Branch branch = branchService.getBranch(branchId);

            if (branch == null) {
                out.println("{\"status\": \"error\", \"message\": \"Branch not found\"}");
                return;
            }

            Bank bank = branch.getBank();
            out.println("{\"status\": \"success\", \"branchId\": " + branch.getBranchId()
                    + ", \"branchName\": \"" + branch.getBranchName()
                    + "\", \"bankId\": " + bank.getBankId()
                    + ", \"bankName\": \"" + bank.getBankName() + "\"}");
        } catch (Exception e) {
            out.println("{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (!"/api/branch".equals(request.getServletPath())) {
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            out.println("{\"status\": \"error\", \"message\": \"Method not allowed\"}");
            return;
        }

        String action = request.getParameter("action");

        if ("create".equals(action)) {
            try {
                int branchId = Integer.parseInt(request.getParameter("branchId"));
                String branchName = request.getParameter("branchName");
                int bankId = Integer.parseInt(request.getParameter("bankId"));

                Bank bank = bankService.getBank(bankId);
                if (bank == null) {
                    out.println("{\"status\": \"error\", \"message\": \"Bank not found\"}");
                    return;
                }

                Branch branch = new Branch(branchId, branchName, bank);
                branchService.createBranch(branch);
                bank.addBranch(branch);

                out.println("{\"status\": \"success\", \"message\": \"Branch created successfully\"}");
            } catch (Exception e) {
                out.println("{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}");
            }
        }
    }
}
