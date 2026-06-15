package servlet;

import service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class RegistrationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        customerService = ApplicationServices.CUSTOMER;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int customerId = Integer.parseInt(request.getParameter("customerId"));
            String fullName = required(request.getParameter("fullName"), "Full name");
            String userId = required(request.getParameter("userId"), "User ID");
            String password = required(request.getParameter("password"), "Password");
            String confirmPassword = request.getParameter("confirmPassword");

            if (!password.equals(confirmPassword)) {
                throw new IllegalArgumentException("Passwords do not match");
            }

            if (password.length() < 8) {
                throw new IllegalArgumentException(
                        "Password must contain at least 8 characters");
            }

            customerService.registerCustomer(
                    customerId,
                    fullName,
                    userId,
                    password,
                    null);

            response.sendRedirect(request.getContextPath()
                    + "/login.html?registered=true");
        } catch (Exception exception) {
            String message = exception.getMessage() == null
                    ? "Registration failed"
                    : exception.getMessage();
            response.sendRedirect(request.getContextPath()
                    + "/login.html?registrationError="
                    + URLEncoder.encode(message, StandardCharsets.UTF_8.name()));
        }
    }

    private String required(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
        return value.trim();
    }
}
