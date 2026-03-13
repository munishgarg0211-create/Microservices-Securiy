package com.munishgarg.microsecurity.book1.ch3_https_hardening;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final String PROJECT = "ch3-https-hardening";
    private static final String BOOK = "book-1-foundations-core-security";
    private static final String OBJECTIVE = "Demonstrate secure HTTPS/TLS hardening and rejection of weak protocols.";
    private static final String CONCEPT = "Transport Layer Security - HTTPS";
    private static final String CONTROL_FAMILY = "TRANSPORT";

    /**
     * Executes a secure HTTPS/TLS hardening demonstration.
     * Enforces the use of modern TLS versions (1.2 or 1.3).
     */
    public Map<String, Object> demo(Map<String, String> params) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);

        String tlsVersion = params.getOrDefault("tlsVersion", "1.0");
        
        // Defense Logic: Enforce high-strength TLS versions and reject deprecated ones (1.0/1.1)
        boolean isStrongTls = tlsVersion.equals("1.2") || tlsVersion.equals("1.3");
        boolean allowed = isStrongTls;

        result.put("tlsVersion", tlsVersion);
        result.put("controlDecision", allowed ? "allow" : "deny");
        result.put("expectedBehavior", "Insecure transport protocols (TLS 1.0/1.1) are rejected at the edge to prevent downgrade attacks.");
        result.put("description", "Secure transport hardening ensures that data remains confidential and tamper-proof during transit.");
        result.put("riskScore", allowed ? 12 : 90);

        return result;
    }
}
