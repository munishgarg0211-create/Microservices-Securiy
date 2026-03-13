package com.munishgarg.microsecurity.book1.ch3_protocol_threat_comparison;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final String PROJECT = "ch3-protocol-threat-comparison";
    private static final String BOOK = "book-1-foundations-core-security";
    private static final String OBJECTIVE = "Demonstrate the security comparison and mitigation effectiveness across different communication protocols.";
    private static final String CONCEPT = "Protocol Security Comparison";
    private static final String CONTROL_FAMILY = "THREAT";

    /**
     * Executes a secure protocol comparison demonstration.
     * Evaluates the residual risk based on the chosen protocol and its inherent security features.
     */
    public Map<String, Object> demo(Map<String, String> params) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);

        String protocol = params.getOrDefault("protocol", "Plain HTTP");
        Map<String, Object> evaluation = evaluateProtocol(protocol);

        // Logic: A secure implementation requires protocols with built-in encryption and authentication
        boolean allowed = (int) evaluation.get("riskScore") < 30;

        result.put("selectedProtocol", protocol);
        result.put("intrinsicSecurity", evaluation.get("features"));
        result.put("controlDecision", allowed ? "approved-transport" : "rejected-transport");
        result.put("expectedBehavior", "Architectural guidelines mandate the use of protocols with intrinsic support for encryption and machine identity.");
        result.put("description", "Secure protocol selection is the foundation of defense-in-depth, ensuring individual packets are protected regardless of network perimeter state.");
        result.put("riskScore", evaluation.get("riskScore"));

        return result;
    }

    private Map<String, Object> evaluateProtocol(String protocol) {
        Map<String, Object> eval = new LinkedHashMap<>();
        switch (protocol) {
            case "gRPC + TLS" -> {
                eval.put("features", "TLS 1.3, Call Credentials, Protobuf Efficiency");
                eval.put("riskScore", 12);
            }
            case "Kafka + SSL/SASL" -> {
                eval.put("features", "Transit Encryption, Producer Identity, ACLs");
                eval.put("riskScore", 15);
            }
            case "HTTPS (TLS 1.2+)" -> {
                eval.put("features", "Strong Encryption, CA-based Trust");
                eval.put("riskScore", 18);
            }
            default -> {
                eval.put("features", "None / Plaintext / Weak");
                eval.put("riskScore", 95);
            }
        }
        return eval;
    }
}
