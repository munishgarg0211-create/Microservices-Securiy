package com.munishgarg.microsecurity.book1.ch3_mtls_baseline;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final String PROJECT = "ch3-mtls-baseline";
    private static final String BOOK = "book-1-foundations-core-security";
    private static final String OBJECTIVE = "Demonstrate secure mutual TLS (mTLS) authentication and client identity verification.";
    private static final String CONCEPT = "Transport Layer Security - mTLS";
    private static final String CONTROL_FAMILY = "TRANSPORT";

    /**
     * Executes a secure mTLS demonstration.
     * Enforces that clients must present a trusted certificate.
     */
    public Map<String, Object> demo(Map<String, String> params) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);

        boolean hasClientCert = Boolean.parseBoolean(params.getOrDefault("clientCertPresent", "false"));
        boolean isCertTrusted = Boolean.parseBoolean(params.getOrDefault("certTrusted", "false"));

        // Defense Logic: mTLS requires both the presence and the trust of the client certificate
        boolean allowed = hasClientCert && isCertTrusted;

        result.put("clientCertPresent", hasClientCert);
        result.put("certTrusted", isCertTrusted);
        result.put("controlDecision", allowed ? "allow" : "deny");
        result.put("expectedBehavior", "Mutual TLS ensures both client and server are cryptographically verified before establishing a connection.");
        result.put("description", "Secure mTLS provides strong machine-to-machine identity, transcending simple IP-based or password authentication.");
        result.put("riskScore", allowed ? 10 : 92);

        return result;
    }
}
