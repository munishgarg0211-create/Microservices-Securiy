package com.munishgarg.microsecurity.book1.ch6_dynamic_policy_engine;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private final PolicyEngine policyEngine;

    public DemoService(PolicyEngine policyEngine) {
        this.policyEngine = policyEngine;
    }

    /**
     * SECURE MODE: Enforces dynamic policies from JSON.
     */
    public Map<String, Object> demoSecure(Map<String, String> params) {
        PolicyEngine.PolicyResult result = policyEngine.evaluate(params);
        return buildResponse("secure", 
            result.allowed() ? "allow" : "block", 
            result.allowed() ? "All dynamic policy gates passed." : "Policy violations detected: " + result.violations(), 
            result.violations(), 
            result.riskScore());
    }

    /**
     * INSECURE MODE: Bypasses policy engine.
     */
    public Map<String, Object> demoInsecure(Map<String, String> params) {
        return buildResponse("insecure", "allow", "Policy engine bypassed. Artifacts deployed without validation.", null, 95);
    }

    private Map<String, Object> buildResponse(String mode, String decision, String behavior, Object data, int risk) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", "ch6-dynamic-policy-engine");
        result.put("mode", mode);
        result.put("controlDecision", decision);
        result.put("expectedBehavior", behavior);
        result.put("violations", data);
        result.put("riskScore", risk);
        return result;
    }
}
