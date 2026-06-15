package servlet;

import model.User;
import model.Customer;
import model.Employee;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

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
                    + ", \"branchId\": " + employee.getBranch().getBranchId();
        }

        out.println("{\"status\": \"success\", \"userId\": \""
                + user.getUserId()
                + "\", \"fullName\": \"" + user.getFullName()
                + "\", \"role\": \"" + user.getRole() + "\""
                + details + "}");
    }
}
