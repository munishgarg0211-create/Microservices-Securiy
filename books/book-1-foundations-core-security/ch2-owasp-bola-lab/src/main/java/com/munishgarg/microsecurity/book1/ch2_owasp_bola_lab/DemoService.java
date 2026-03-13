package com.munishgarg.microsecurity.book1.ch2_owasp_bola_lab;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final String PROJECT = "ch2-owasp-bola-lab";
    private static final String BOOK = "book-1-foundations-core-security";
    private static final String OBJECTIVE = "Demonstrate Broken Object Level Authorization (BOLA) and its secure mitigation.";
    private static final String CONCEPT = "OWASP Top 10 - BOLA";
    private static final String CONTROL_FAMILY = "AUTHZ";

    /**
     * Executes a secure BOLA demonstration.
     * In a production system, 'actor' would be resolved from a verified JWT/Principal,
     * and 'resourceId' would be used to fetch the resource + its owner from a database.
     */
    public Map<String, Object> demo(Map<String, String> params) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);

        // Simulation parameters
        String actor = params.getOrDefault("actor", "alice"); 
        String resourceId = params.getOrDefault("resourceId", "bob_profile");
        String owner = "bob"; // Mocking that 'bob_profile' belongs to 'bob'

        // Defense Logic: Verify that the requesting actor is the actual owner
        boolean isOwner = actor.equals(owner);
        boolean allowed = isOwner;

        result.put("actor", actor);
        result.put("resourceId", resourceId);
        result.put("resourceOwner", owner);
        result.put("controlDecision", allowed ? "allow" : "deny");
        result.put("expectedBehavior", "Access is strictly limited to the resource owner or an authorized administrator.");
        result.put("description", "Secure Object-Level Authorization ensures that users can only access data they own.");
        result.put("riskScore", allowed ? 10 : 95); // High risk score if we bypass this check (BOLA impact)

        return result;
    }
}
