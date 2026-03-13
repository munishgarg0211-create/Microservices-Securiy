package com.munishgarg.microsecurity.book1.ch1_role_based_learning_labs;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final String PROJECT = "ch1-role-based-learning-labs";
    private static final String BOOK = "book-1-foundations-core-security";
    private static final String OBJECTIVE = "Demonstrate role-based access control (RBAC) across different engineering roles (Dev, Sec, Ops).";
    private static final String CONCEPT = "Role-Based Security Lab";
    private static final String CONTROL_FAMILY = "AUTHZ";

    /**
     * Executes a role-based access control demonstration.
     * Enforces that only authorized roles can perform specific sensitive operations.
     */
    public Map<String, Object> demo(Map<String, String> params) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);

        String role = params.getOrDefault("role", "Developer");
        String action = params.getOrDefault("action", "read-code");

        // Defense Logic: Enforce strict role-to-action mapping
        boolean allowed = checkRoleAction(role, action);

        result.put("userRole", role);
        result.put("requestedAction", action);
        result.put("controlDecision", allowed ? "allow" : "deny");
        result.put("expectedBehavior", "Access control must be granular; Developers can only push code, SecOps can audit, and SRE can deploy, with no unauthorized overlap.");
        result.put("description", "Secure RBAC ensures that users are granted the minimum level of access required for their specific role, preventing privilege escalation.");
        result.put("riskScore", allowed ? 12 : 94);

        return result;
    }

    private boolean checkRoleAction(String role, String action) {
        return switch (role) {
            case "Developer" -> action.equals("read-code") || action.equals("write-code");
            case "Security" -> action.equals("audit-logs") || action.equals("view-vulnerabilities");
            case "SRE" -> action.equals("deploy-service") || action.equals("view-metrics");
            default -> false;
        };
    }
}
