package filter;

import ratelimiter.RateLimiter;
import ratelimiter.RateLimiter.RateLimitResult;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RateLimitFilter implements Filter {

    private final RateLimiter rateLimiter = new RateLimiter();

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

        /*
         * Servlet filters are the correct place for login rate limiting because
         * they run before LoginServlet. That lets the application reject abusive
         * login traffic before authentication and repository work is performed,
         * while keeping LoginServlet focused on login behavior.
         */
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String clientIp = request.getRemoteAddr();
        RateLimitResult result = rateLimiter.checkRequest(clientIp);

        if (result.isAllowed()) {
            filterChain.doFilter(request, response);
            return;
        }

        response.setStatus(429);
        response.setHeader("Retry-After",
                String.valueOf(result.getRetryAfterSeconds()));
        response.setContentType("application/json");
        response.getWriter().println("{"
                + "\"success\": false, "
                + "\"message\": \"Too many login attempts. "
                + "Please try again after one minute.\""
                + "}");
    }

    @Override
    public void destroy() {
    }
}
