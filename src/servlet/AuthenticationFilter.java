package servlet;

import model.User;
import service.EmployeeAuthorizationService;

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

public class AuthenticationFilter implements Filter {

    private final EmployeeAuthorizationService authorizationService =
            new EmployeeAuthorizationService();

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
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED,
                    "Authentication required");
            return;
        }

        if (!isAuthorized(request, user)) {
            sendError(response, HttpServletResponse.SC_FORBIDDEN,
                    "You are not authorized to perform this action");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isAuthorized(HttpServletRequest request, User user) {
        return authorizationService.isAllowed(user, request);
    }

    private void sendError(
            HttpServletResponse response,
            int status,
            String message) throws IOException {

        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().println(
                "{\"status\": \"error\", \"message\": \"" + message + "\"}");
    }

    @Override
    public void destroy() {
    }
}
