package com.munishgarg.microsecurity.book1.ch1_principles_scorecard;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final String PROJECT = "ch1-principles-scorecard";
    private static final String BOOK = "book-1-foundations-core-security";
    private static final String OBJECTIVE = "Evaluate system architecture against core microservice security principles.";
    private static final String CONCEPT = "Security Principles Scorecard";
    private static final String CONTROL_FAMILY = "GOVERNANCE";

    /**
     * Executes a security principles evaluation.
     * Scores the implementation of Defense-in-Depth, Least Privilege, and Secure Defaults.
     */
    public Map<String, Object> demo(Map<String, String> params) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);

        boolean defenseInDepth = Boolean.parseBoolean(params.getOrDefault("defenseInDepth", "true"));
        boolean leastPrivilege = Boolean.parseBoolean(params.getOrDefault("leastPrivilege", "true"));
        boolean failSecurely = Boolean.parseBoolean(params.getOrDefault("failSecurely", "true"));

        // Logic: A robust system must adhere to all core security principles
        boolean isArchitectureSecure = defenseInDepth && leastPrivilege && failSecurely;
        int complianceScore = (defenseInDepth ? 33 : 0) + (leastPrivilege ? 33 : 0) + (failSecurely ? 34 : 0);

        result.put("defenseInDepth", defenseInDepth);
        result.put("leastPrivilege", leastPrivilege);
        result.put("failSecurely", failSecurely);
        result.put("complianceScore", complianceScore + "%");
        result.put("controlDecision", isArchitectureSecure ? "compliant" : "non-compliant");
        result.put("expectedBehavior", "Architectures must enforce multi-layered defense and restricted permissions by default to maintain a resilient security posture.");
        result.put("description", "The principles scorecard provides a high-level assessment of how well the system's design aligns with established security best practices.");
        result.put("riskScore", isArchitectureSecure ? 15 : 85);

        return result;
    }
}
