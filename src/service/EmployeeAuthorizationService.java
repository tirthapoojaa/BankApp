package service;

import enums.EmployeeRole;
import enums.Role;
import model.Employee;
import model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class EmployeeAuthorizationService {

    public boolean isAllowed(
            User user,
            HttpServletRequest request) {

        if (user.getRole() == Role.CUSTOMER) {
            return isCustomerAllowed(request);
        }

        if (!(user instanceof Employee)) {
            return false;
        }

        EmployeeRole role = ((Employee) user).getEmployeeRole();
        String permission = permissionFor(request);
        return permission != null && permissionsFor(role).contains(permission);
    }

    public Set<String> permissionsFor(EmployeeRole role) {

        EmployeeRole normalizedRole = normalize(role);

        if (normalizedRole == EmployeeRole.BRANCH_MANAGER) {
            return permissions(
                    "dashboard",
                    "assignedBranch",
                    "createBank",
                    "viewBank",
                    "createBranch",
                    "viewBranch",
                    "createEmployee",
                    "viewEmployee",
                    "createCustomer",
                    "viewCustomer",
                    "createAccount",
                    "viewAccount",
                    "branchAccounts",
                    "deposit",
                    "withdraw",
                    "transactionHistory");
        }

        if (normalizedRole == EmployeeRole.RELATIONSHIP_MANAGER) {
            return permissions(
                    "dashboard",
                    "assignedBranch",
                    "createBranch",
                    "viewBranch",
                    "createCustomer",
                    "viewCustomer",
                    "createAccount",
                    "viewAccount",
                    "branchAccounts",
                    "transactionHistory");
        }

        return permissions(
                "dashboard",
                "assignedBranch",
                "viewCustomer",
                "viewAccount",
                "branchAccounts",
                "deposit",
                "withdraw",
                "transactionHistory");
    }

    public EmployeeRole normalize(EmployeeRole role) {

        if (role == EmployeeRole.RELATION_MANAGER) {
            return EmployeeRole.RELATIONSHIP_MANAGER;
        }
        return role;
    }

    private boolean isCustomerAllowed(HttpServletRequest request) {

        String path = request.getServletPath();
        return "/api/auth".equals(path)
                || "/api/account".equals(path)
                || "/api/transaction".equals(path);
    }

    private String permissionFor(HttpServletRequest request) {

        String path = request.getServletPath();
        String action = request.getParameter("action");
        String method = request.getMethod();

        if ("/api/auth".equals(path)) {
            return "dashboard";
        }
        if ("/api/bank".equals(path)) {
            return "POST".equals(method) ? "createBank" : "viewBank";
        }
        if ("/api/branch".equals(path)) {
            return "POST".equals(method) ? "createBranch" : "viewBranch";
        }
        if ("/api/customer".equals(path)) {
            return "POST".equals(method) ? "createCustomer" : "viewCustomer";
        }
        if ("/api/employee".equals(path)) {
            if ("assignedBranch".equals(action)) {
                return "assignedBranch";
            }
            return "POST".equals(method) ? "createEmployee" : "viewEmployee";
        }
        if ("/api/account".equals(path)) {
            if ("POST".equals(method)) {
                return "createAccount";
            }
            if ("branchAccounts".equals(action)) {
                return "branchAccounts";
            }
            return "viewAccount";
        }
        if ("/api/transaction".equals(path)) {
            if ("POST".equals(method)) {
                if ("deposit".equals(action)) {
                    return "deposit";
                }
                if ("withdraw".equals(action)) {
                    return "withdraw";
                }
            }
            return "transactionHistory";
        }

        return null;
    }

    private Set<String> permissions(String... permissions) {

        return new LinkedHashSet<>(Arrays.asList(permissions));
    }
}
