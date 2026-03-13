package com.munishgarg.microsecurity.book1.ch3_grpc_tls_authz;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final String PROJECT = "ch3-grpc-tls-authz";
    private static final String BOOK = "book-1-foundations-core-security";
    private static final String OBJECTIVE = "Demonstrate secure gRPC communication using TLS and call-level authorization.";
    private static final String CONCEPT = "gRPC Security - TLS and Authz";
    private static final String CONTROL_FAMILY = "TRANSPORT";

    /**
     * Executes a secure gRPC demonstration.
     * Enforces both transport layer security (TLS) and individual call authorization.
     */
    public Map<String, Object> demo(Map<String, String> params) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);

        boolean tlsEnabled = Boolean.parseBoolean(params.getOrDefault("tlsEnabled", "false"));
        boolean callTokenValid = Boolean.parseBoolean(params.getOrDefault("callTokenValid", "false"));

        // Defense Logic: Secure gRPC requires an encrypted channel AND a verified call credential
        boolean allowed = tlsEnabled && callTokenValid;

        result.put("tlsEnabled", tlsEnabled);
        result.put("callTokenValid", callTokenValid);
        result.put("controlDecision", allowed ? "allow" : "deny");
        result.put("expectedBehavior", "gRPC calls must be encapsulated within a TLS session and accompanied by a valid metadata token (Bearer/JWT) for per-rpc authorization.");
        result.put("description", "Secure gRPC implementation ensures that machine-to-machine remote procedure calls are both private and strictly authorized.");
        result.put("riskScore", allowed ? 15 : 95);

        return result;
    }
}
