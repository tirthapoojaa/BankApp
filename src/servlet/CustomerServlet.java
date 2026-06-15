package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import service.*;
import model.*;

public class CustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerService customerService;

    public void init() throws ServletException {
        customerService = ApplicationServices.CUSTOMER;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");

        if ("create".equals(action)) {
            try {
                int customerId = Integer.parseInt(request.getParameter("customerId"));
                String name = request.getParameter("name");
                String username = request.getParameter("username");
                String password = request.getParameter("password");

                Customer customer = new Customer(customerId, name, username, password, null);
                customerService.createCustomer(customer);

                out.println("{\"status\": \"success\", \"message\": \"Customer created successfully\"}");
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
                int customerId = Integer.parseInt(request.getParameter("customerId"));
                Customer customer = customerService.getCustomer(customerId);

                if (customer != null) {
                    out.println("{\"status\": \"success\", \"customerId\": " + customer.getUserId()
                            + ", \"name\": \"" + customer.getName()
                            + "\", \"username\": \"" + customer.getUsername() + "\"}");
                } else {
                    out.println("{\"status\": \"error\", \"message\": \"Customer not found\"}");
                }
            } catch (Exception e) {
                out.println("{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}");
            }
        }
    }
}
