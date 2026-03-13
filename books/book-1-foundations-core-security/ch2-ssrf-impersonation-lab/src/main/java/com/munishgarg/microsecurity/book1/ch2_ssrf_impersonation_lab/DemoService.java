package com.munishgarg.microsecurity.book1.ch2_ssrf_impersonation_lab;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final String PROJECT = "ch2-ssrf-impersonation-lab";
    private static final String BOOK = "book-1-foundations-core-security";
    private static final String OBJECTIVE = "Demonstrate Server-Side Request Forgery (SSRF) prevention.";
    private static final String CONCEPT = "OWASP Top 10 - SSRF";
    private static final String CONTROL_FAMILY = "THREAT";

    /**
     * Executes a secure SSRF demonstration.
     * Prevents requests to internal metadata services or private IP ranges.
     */
    public Map<String, Object> demo(Map<String, String> params) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);

        String targetUrl = params.getOrDefault("url", "http://internal-metadata-service/secret");
        
        // Defense Logic: Block internal metadata and private IP ranges
        boolean isInternal = targetUrl.contains("internal") || 
                            targetUrl.contains("169.254") || 
                            targetUrl.contains("127.0.0.1") || 
                            targetUrl.contains("localhost");

        boolean allowed = !isInternal;

        result.put("targetUrl", targetUrl);
        result.put("isInternalResource", isInternal);
        result.put("controlDecision", allowed ? "allow" : "deny");
        result.put("expectedBehavior", "Outbound requests to protected internal networks are blocked by an allow-list or restrictive pattern matching.");
        result.put("description", "Secure SSRF prevention ensures the application cannot be used as a proxy to attack internal infrastructure.");
        result.put("riskScore", allowed ? 15 : 98);

        return result;
    }
}
