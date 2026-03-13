package com.munishgarg.microsecurity.book1.ch2_supply_chain_mini_sim;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final String PROJECT = "ch2-supply-chain-mini-sim";
    private static final String BOOK = "book-1-foundations-core-security";
    private static final String OBJECTIVE = "Demonstrate secure supply-chain gates using artifact validation (SBOM and Signing).";
    private static final String CONCEPT = "Supply Chain Security";
    private static final String CONTROL_FAMILY = "POLICY";

    /**
     * Executes a secure supply chain demonstration.
     * Enforces that artifacts must be signed and possess a valid SBOM.
     */
    public Map<String, Object> demo(Map<String, String> params) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);

        boolean imageSigned = Boolean.parseBoolean(params.getOrDefault("imageSigned", "false"));
        boolean hasSbom = Boolean.parseBoolean(params.getOrDefault("hasSbom", "false"));

        // Defense Logic: Enforce that only validated artifacts (signed + SBOM) are allowed to proceed
        boolean allowed = imageSigned && hasSbom;

        result.put("imageSigned", imageSigned);
        result.put("hasSbom", hasSbom);
        result.put("controlDecision", allowed ? "allow" : "deny");
        result.put("expectedBehavior", "Artifacts must pass cryptographic signing verification and metadata (SBOM) presence checks before deployment.");
        result.put("description", "Secure supply chain gates prevent unverified or tampered code from reaching production environments.");
        result.put("riskScore", allowed ? 18 : 94);

        return result;
    }
}
