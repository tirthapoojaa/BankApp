package servlet;

import controller.AuthenticationController;
import enums.Role;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private AuthenticationController authenticationController;

    @Override
    public void init() throws ServletException {
        authenticationController =
                new AuthenticationController(ApplicationServices.AUTHENTICATION);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String roleValue = request.getParameter("role");
        User user = authenticationController.login(userId, password);

        if (user == null || roleValue == null
                || !user.getRole().name().equals(roleValue)) {
            response.sendRedirect(request.getContextPath()
                    + "/login.html?error=invalid");
            return;
        }

        HttpSession existingSession = request.getSession(false);
        if (existingSession != null) {
            existingSession.invalidate();
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        session.setMaxInactiveInterval(30 * 60);

        String dashboard = user.getRole() == Role.EMPLOYEE
                ? "/employee-dashboard.html"
                : "/customer-dashboard.html";
        response.sendRedirect(request.getContextPath() + dashboard);
    }
}
