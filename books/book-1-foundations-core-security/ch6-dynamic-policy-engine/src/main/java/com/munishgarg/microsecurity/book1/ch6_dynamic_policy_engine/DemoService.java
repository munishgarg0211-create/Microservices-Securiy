package com.munishgarg.microsecurity.book1.ch6_dynamic_policy_engine;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private final PolicyEngine policyEngine;

    private static final String PROJECT = "ch6-dynamic-policy-engine";
    private static final String BOOK = "book-1-foundations-core-security";
    private static final String OBJECTIVE = "Implement a dynamic policy engine for externalized, runtime-adjustable security rules.";
    private static final String CONCEPT = "Externalized Policy Management";
    private static final String CONTROL_FAMILY = "POLICY";

    public DemoService(PolicyEngine policyEngine) {
        this.policyEngine = policyEngine;
    }

    /**
     * Executes a dynamic policy evaluation.
     * Enforces externalized rules loaded from a separate configuration source.
     */
    public Map<String, Object> demo(Map<String, String> params) {
        PolicyEngine.PolicyResult result = policyEngine.evaluate(params);
        return buildResponse(
            result.allowed() ? "allow" : "block", 
            result.allowed() ? "All dynamic policy gates passed." : "Policy violations detected: " + result.violations(), 
            result.violations(), 
            result.riskScore());
    }

    private Map<String, Object> buildResponse(String decision, String behavior, Object data, int risk) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);
        result.put("controlDecision", decision);
        result.put("expectedBehavior", behavior);
        result.put("violations", data);
        result.put("riskScore", risk);
        return result;
    }
}
