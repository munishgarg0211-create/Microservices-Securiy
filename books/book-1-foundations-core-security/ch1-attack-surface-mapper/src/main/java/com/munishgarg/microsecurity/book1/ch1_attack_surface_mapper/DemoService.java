package com.munishgarg.microsecurity.book1.ch1_attack_surface_mapper;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final String PROJECT = "ch1-attack-surface-mapper";
    private static final String BOOK = "book-1-foundations-core-security";
    private static final String OBJECTIVE = "Generate attack-surface map from APIs and service topology.";
    private static final String CONCEPT = "Attack Surface Analysis";
    private static final String CONTROL_FAMILY = "THREAT";

    /**
     * Executes a secure attack surface analysis demonstration.
     * Evaluates the visibility and exposure of services and APIs.
     */
    public Map<String, Object> demo(Map<String, String> params) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);

        int totalEndpoints = Integer.parseInt(params.getOrDefault("endpoints", "15"));
        int exposedEndpoints = Integer.parseInt(params.getOrDefault("exposed", "2"));
        boolean mfaEnforced = Boolean.parseBoolean(params.getOrDefault("mfaEnforced", "true"));

        // Defense Logic: A secure attack surface is minimized and strictly controlled
        boolean isSurfaceOptimized = (exposedEndpoints <= 2) && mfaEnforced;
        
        result.put("totalEndpoints", totalEndpoints);
        result.put("exposedEndpoints", exposedEndpoints);
        result.put("mfaEnforced", mfaEnforced);
        result.put("controlDecision", isSurfaceOptimized ? "optimized" : "over-exposed");
        result.put("expectedBehavior", "The attack surface should be minimized by hiding internal APIs and enforcing strong authentication (MFA) on all public entry points.");
        result.put("description", "Secure attack surface mapping identifies entry points and ensures that each is protected by appropriate security controls to reduce the overall exploitability of the system.");
        result.put("riskScore", isSurfaceOptimized ? 18 : 88);

        return result;
    }
}
