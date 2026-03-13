package com.munishgarg.microsecurity.book1.ch1_sdlc_security_pipeline;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final String PROJECT = "ch1-sdlc-security-pipeline";
    private static final String BOOK = "book-1-foundations-core-security";
    private static final String OBJECTIVE = "Demonstrate secure SDLC pipeline gates including SAST, DAST, and dependency scanning.";
    private static final String CONCEPT = "Secure SDLC Pipeline";
    private static final String CONTROL_FAMILY = "POLICY";

    /**
     * Executes a secure pipeline gate demonstration.
     * Enforces that all security scans must pass before an artifact can be promoted.
     */
    public Map<String, Object> demo(Map<String, String> params) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);

        boolean sastPassed = Boolean.parseBoolean(params.getOrDefault("sastPassed", "true"));
        boolean scaPassed = Boolean.parseBoolean(params.getOrDefault("scaPassed", "true"));
        int criticalVulns = Integer.parseInt(params.getOrDefault("criticalVulns", "0"));

        // Defense Logic: Secure pipelines require clean scans and zero critical vulnerabilities
        boolean pipelineSuccess = sastPassed && scaPassed && (criticalVulns == 0);

        result.put("sastStatus", sastPassed ? "SUCCESS" : "FAILURE");
        result.put("scaStatus", scaPassed ? "SUCCESS" : "FAILURE");
        result.put("criticalVulnsCount", criticalVulns);
        result.put("controlDecision", pipelineSuccess ? "pass" : "block");
        result.put("expectedBehavior", "The pipeline must act as a quality gate, automatically blocking any build that contains static analysis failures or high-severity vulnerabilities.");
        result.put("description", "Secure SDLC integration ensures that security is shifted left, preventing vulnerable code from reaching production environments.");
        result.put("riskScore", pipelineSuccess ? 10 : 90);

        return result;
    }
}
