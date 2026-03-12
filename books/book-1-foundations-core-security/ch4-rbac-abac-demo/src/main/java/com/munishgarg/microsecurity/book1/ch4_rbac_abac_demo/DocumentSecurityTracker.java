package com.munishgarg.microsecurity.book1.ch4_rbac_abac_demo;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simulates a data store or a policy decision point (PDP) for Attribute-Based Access Control (ABAC).
 * Evaluates whether a specific username is the owner of a specific document ID.
 */
@Component("docSecurity")
public class DocumentSecurityTracker {

    // Simple mock database mapping document ID to owning username
    private final Map<String, String> documentOwners = new ConcurrentHashMap<>();

    public DocumentSecurityTracker() {
        // Pre-populate mock data
        documentOwners.put("doc-1", "alice");
        documentOwners.put("doc-2", "bob");
    }

    /**
     * SpEL-callable method to check ownership.
     * Used in @PreAuthorize("@docSecurity.isOwner(#documentId, authentication.name)")
     */
    public boolean isOwner(String documentId, String username) {
        if (documentId == null || username == null) {
            return false;
        }
        String owner = documentOwners.get(documentId);
        return username.equals(owner);
    }
}
