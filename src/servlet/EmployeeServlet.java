package servlet;

import enums.EmployeeRole;
import model.Branch;
import model.Employee;
import model.User;
import service.BranchService;
import service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class EmployeeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private EmployeeService employeeService;
    private BranchService branchService;

    @Override
    public void init() throws ServletException {
        employeeService = ApplicationServices.EMPLOYEE;
        branchService = ApplicationServices.BRANCH;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        boolean publicRegistration = "register".equals(action);
        boolean publicPath = "/register-employee".equals(
                request.getServletPath());

        if ((publicPath && !publicRegistration)
                || (!publicPath && publicRegistration)
                || (!publicRegistration && !"create".equals(action))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            out.println("{\"status\": \"error\", \"message\": \"Invalid action\"}");
            return;
        }

        try {
            int employeeId = Integer.parseInt(request.getParameter("employeeId"));
            String fullName = request.getParameter("name");
            String userId = request.getParameter("userId");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            String branchIdValue = request.getParameter("branchId");
            EmployeeRole employeeRole = employeeRole(
                    request.getParameter("employeeRole"));
            double salary = Double.parseDouble(request.getParameter("salary"));

            if (publicRegistration && !password.equals(confirmPassword)) {
                throw new IllegalArgumentException("Passwords do not match");
            }

            Branch branch = branchService.getBranch(Integer.parseInt(branchIdValue));
            if (branch == null) {
                out.println("{\"status\": \"error\", \"message\": \"Branch not found\"}");
                return;
            }

            employeeService.registerEmployee(
                    employeeId,
                    fullName,
                    userId,
                    password,
                    branch,
                    employeeRole,
                    salary);

            if (publicRegistration) {
                response.sendRedirect(request.getContextPath()
                        + "/login.html?employeeRegistered=true");
            } else {
                out.println("{\"status\": \"success\", \"message\": "
                        + "\"Employee created successfully\"}");
            }
        } catch (Exception exception) {
            if (publicRegistration) {
                String message = exception.getMessage() == null
                        ? "Employee registration failed"
                        : exception.getMessage();
                response.sendRedirect(request.getContextPath()
                        + "/login.html?employeeRegistrationError="
                        + URLEncoder.encode(
                                message, StandardCharsets.UTF_8.name()));
            } else {
                out.println("{\"status\": \"error\", \"message\": \""
                        + exception.getMessage() + "\"}");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            if ("assignedBranch".equals(request.getParameter("action"))) {
                User user = (User) request.getSession().getAttribute("user");
                Employee employee = (Employee) user;
                Branch branch = employee.getBranch();

                out.println("{\"status\": \"success\", \"branchId\": "
                        + branch.getBranchId()
                        + ", \"branchName\": \"" + branch.getBranchName()
                        + "\", \"bankId\": " + branch.getBank().getBankId()
                        + ", \"bankName\": \"" + branch.getBank().getBankName()
                        + "\", \"designation\": \"" + employee.getDesignation()
                        + "\", \"salary\": " + employee.getSalary() + "}");
                return;
            }

            int employeeId = Integer.parseInt(request.getParameter("employeeId"));
            Employee employee = employeeService.getEmployee(employeeId);

            if (employee == null) {
                out.println("{\"status\": \"error\", \"message\": \"Employee not found\"}");
                return;
            }

            out.println("{\"status\": \"success\", \"employeeId\": "
                    + employee.getEmployeeId()
                    + ", \"name\": \"" + employee.getFullName()
                    + "\", \"userId\": \"" + employee.getUserId()
                    + "\", \"employeeRole\": \"" + employee.getEmployeeRole()
                    + "\", \"branchId\": " + employee.getBranch().getBranchId()
                    + ", \"salary\": " + employee.getSalary() + "}");
        } catch (Exception exception) {
            out.println("{\"status\": \"error\", \"message\": \""
                    + exception.getMessage() + "\"}");
        }
    }

    private EmployeeRole employeeRole(String value) {

        if ("RELATION_MANAGER".equals(value)) {
            return EmployeeRole.RELATIONSHIP_MANAGER;
        }
        return EmployeeRole.valueOf(value);
    }
}
