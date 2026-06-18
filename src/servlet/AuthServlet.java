package servlet;

import model.User;
import model.Customer;
import model.Employee;
import service.EmployeeAuthorizationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final EmployeeAuthorizationService authorizationService =
            new EmployeeAuthorizationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        User user = (User) request.getSession().getAttribute("user");

        String details = "";
        if (user instanceof Customer) {
            details = ", \"customerId\": " + ((Customer) user).getCustomerId();
        } else if (user instanceof Employee) {
            Employee employee = (Employee) user;
            details = ", \"employeeId\": " + employee.getEmployeeId()
                    + ", \"branchId\": " + employee.getBranch().getBranchId()
                    + ", \"employeeRole\": \""
                    + authorizationService.normalize(employee.getEmployeeRole())
                    + "\", \"permissions\": "
                    + permissionsJson(employee);
        }

        out.println("{\"status\": \"success\", \"userId\": \""
                + user.getUserId()
                + "\", \"fullName\": \"" + user.getFullName()
                + "\", \"role\": \"" + user.getRole() + "\""
                + details + "}");
    }

    private String permissionsJson(Employee employee) {

        StringBuilder json = new StringBuilder("[");
        int index = 0;
        for (String permission : authorizationService.permissionsFor(
                employee.getEmployeeRole())) {
            if (index++ > 0) {
                json.append(',');
            }
            json.append('"').append(permission).append('"');
        }
        json.append(']');
        return json.toString();
    }
}
