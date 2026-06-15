package servlet;

import enums.Role;
import model.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DashboardFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.html");
            return;
        }

        String requestedDashboard = request.getServletPath();
        boolean employeeDashboard = "/employee-dashboard.html".equals(requestedDashboard);
        Role requiredRole = employeeDashboard ? Role.EMPLOYEE : Role.CUSTOMER;

        if (user.getRole() != requiredRole) {
            String correctDashboard = user.getRole() == Role.EMPLOYEE
                    ? "/employee-dashboard.html"
                    : "/customer-dashboard.html";
            response.sendRedirect(request.getContextPath() + correctDashboard);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
