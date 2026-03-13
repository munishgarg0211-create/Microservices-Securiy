package com.munishgarg.microsecurity.book1.ch2_stride_control_mapper;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final String PROJECT = "ch2-stride-control-mapper";
    private static final String BOOK = "book-1-foundations-core-security";
    private static final String OBJECTIVE = "Demonstrate the mapping of STRIDE threats to verified security controls.";
    private static final String CONCEPT = "Threat Modeling - STRIDE";
    private static final String CONTROL_FAMILY = "THREAT";

    /**
     * Executes a secure demonstration of STRIDE threat mapping.
     * Ensures that every identified STRIDE threat is mapped to a production-grade control.
     */
    public Map<String, Object> demo(Map<String, String> params) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);

        String threat = params.getOrDefault("threat", "Spoofing");
        String control = mapThreatToControl(threat);

        // Logic: Every threat in the STRIDE model must have a corresponding mitigation
        boolean isMapped = !control.contains("Verify");

        result.put("threat", threat);
        result.put("mappedControl", control);
        result.put("controlDecision", isMapped ? "mitigated" : "exposed");
        result.put("expectedBehavior", "STRIDE threats are systematically mapped to and neutralized by verified architectural controls.");
        result.put("description", "Systematic threat modeling ensures that potential attack vectors are identified and mitigated early in the design phase.");
        result.put("riskScore", isMapped ? 15 : 85);

        return result;
    }

    private String mapThreatToControl(String threat) {
        return switch (threat) {
            case "Spoofing" -> "Authentication / Identity (JWT, mTLS)";
            case "Tampering" -> "Integrity / Signing (HMAC, Digital Signatures)";
            case "Repudiation" -> "Non-Repudiation (Audit Logs, Immutable Storage)";
            case "Information Disclosure" -> "Confidentiality (TLS, Encryption at Rest)";
            case "Denial of Service" -> "Availability (Rate Limiting, Circuit Breakers)";
            case "Elevation of Privilege" -> "Authorization (RBAC, ABAC)";
            default -> "Verify against STRIDE baseline - threat not recognized";
        };
    }
}
