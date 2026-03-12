package com.munishgarg.microsecurity.book1.ch4_jwt_validation_rotation;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final String PROJECT = "ch4-jwt-validation-rotation";
    private static final String BOOK = "book-1-foundations-core-security";
    private static final String OBJECTIVE = "Validate JWT and rotate signing keys safely.";
    private static final String CONCEPT = "Jwt Validation Rotation";
    private static final String CONTROL_FAMILY = "AUTHZ";

    // Production copy/paste checklist:
    // 1) Read actor from JWT/security context, never from request params.
    // 2) Read resource owner/ACL from DB or trusted service.
    // 3) Throw AccessDeniedException on unauthorized access before loading sensitive payloads.

    public Map<String, Object> demoSecure(Jwt jwt, String owner) {
        // Extract actor from the validated JWT subject
        String actor = jwt.getSubject();
        if (actor == null) {
            actor = "unknown";
        }
        
        // Example check: Is the actor the owner of the resource, or do they have admin scopes?
        boolean authorizedByOwnership = actor.equals(owner);
        boolean isAdmin = jwt.getClaimAsStringList("scope") != null && jwt.getClaimAsStringList("scope").contains("admin");

        if (!authorizedByOwnership && !isAdmin) {
            // Good practice: Deny access if they don't own the resource and aren't an admin
            throw new AccessDeniedException("User " + actor + " is not authorized to access resource owned by " + owner);
        }

        return buildResult("secure", actor, owner, true, "owner or admin only access is enforced", 25);
    }

    public Map<String, Object> demoInsecure(String actor, String owner) {
        // Bad practice: Allow access regardless of ownership (BOLA-style flaw)
        // Trusting the 'actor' parameter from the URL query instead of a secure context
        return buildResult("insecure", actor, owner, true, "authorization bypass demonstrates potential OWASP BOLA impact", 95);
    }

    private Map<String, Object> buildResult(String mode, String actor, String owner, boolean allowed, String behavior, int risk) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("status", "sample-ready");
        result.put("secureControl", "secure".equals(mode) ? "enabled" : "disabled");
        result.put("mode", mode);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);
        result.put("scenario", "object-level-authorization");
        result.put("actor", actor);
        result.put("resourceOwner", owner);
        result.put("controlDecision", allowed ? "allow" : "deny");
        result.put("expectedBehavior", behavior);
        result.put("riskScore", risk);
        return result;
    }
}
