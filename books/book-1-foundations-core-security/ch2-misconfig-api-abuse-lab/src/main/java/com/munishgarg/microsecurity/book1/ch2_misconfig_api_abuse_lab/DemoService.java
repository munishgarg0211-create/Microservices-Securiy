package com.munishgarg.microsecurity.book1.ch2_misconfig_api_abuse_lab;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final String PROJECT = "ch2-misconfig-api-abuse-lab";
    private static final String BOOK = "book-1-foundations-core-security";
    private static final String OBJECTIVE = "Demonstrate secure mitigation against API misconfiguration and brute-force abuse.";
    private static final String CONCEPT = "OWASP Top 10 - Security Misconfiguration";
    private static final String CONTROL_FAMILY = "THREAT";

    /**
     * Executes a secure API abuse demonstration.
     * Enforces rate limiting/throttling when threshold is exceeded.
     */
    public Map<String, Object> demo(Map<String, String> params) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);

        int attempts = Integer.parseInt(params.getOrDefault("attempts", "1"));
        int threshold = 5;

        // Defense Logic: Enforce throttling when attempts exceed the security threshold
        boolean throttled = (attempts > threshold);
        boolean allowed = !throttled;

        result.put("loginAttempts", attempts);
        result.put("abuseThreshold", threshold);
        result.put("controlDecision", allowed ? "allow" : "throttle");
        result.put("expectedBehavior", "Security controls automatically throttle or block requests when a brute-force threshold is met.");
        result.put("description", "Secure configuration ensures that the API is protected against automated abuse and brute-force attacks.");
        result.put("riskScore", allowed ? 10 : 92);

        return result;
    }
}
