package com.munishgarg.microsecurity.book1.ch3_kafka_security_baseline;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final String PROJECT = "ch3-kafka-security-baseline";
    private static final String BOOK = "book-1-foundations-core-security";
    private static final String OBJECTIVE = "Demonstrate secure Kafka implementation using encrypted transport, SASL authentication, and ACL enforcement.";
    private static final String CONCEPT = "Event Bus Security - Kafka";
    private static final String CONTROL_FAMILY = "MESSAGING";

    /**
     * Executes a secure Kafka demonstration.
     * Enforces three layers of security: SSL/TLS transport, SASL authentication, and ACL authorization.
     */
    public Map<String, Object> demo(Map<String, String> params) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);

        boolean sslEnabled = Boolean.parseBoolean(params.getOrDefault("sslEnabled", "false"));
        boolean saslAuthenticated = Boolean.parseBoolean(params.getOrDefault("saslAuthenticated", "false"));
        boolean aclAuthorized = Boolean.parseBoolean(params.getOrDefault("aclAuthorized", "false"));

        // Defense Logic: Secure messaging requires encryption, identity, and explicit permission
        boolean allowed = sslEnabled && saslAuthenticated && aclAuthorized;

        result.put("sslEnabled", sslEnabled);
        result.put("saslAuthenticated", saslAuthenticated);
        result.put("aclAuthorized", aclAuthorized);
        result.put("controlDecision", allowed ? "allow" : "deny");
        result.put("expectedBehavior", "Kafka producers and consumers must use SSL for transit encryption, SASL for authentication, and meet ACL requirements for topic access.");
        result.put("description", "Secure Kafka implementation protects the event bus from data eavesdropping, unauthorized producers, and downstream consumer interference.");
        result.put("riskScore", allowed ? 14 : 96);

        return result;
    }
}
