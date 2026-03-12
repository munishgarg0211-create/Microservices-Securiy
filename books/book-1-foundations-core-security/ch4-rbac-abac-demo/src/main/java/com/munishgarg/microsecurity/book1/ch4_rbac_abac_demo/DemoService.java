package com.munishgarg.microsecurity.book1.ch4_rbac_abac_demo;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    public Map<String, String> getAdminSettings(String username) {
        Map<String, String> settings = new LinkedHashMap<>();
        settings.put("status", "success");
        settings.put("dataType", "Administrative Settings");
        settings.put("accessedBy", username);
        settings.put("message", "You have successfully passed the RBAC check (must possess ROLE_ADMIN).");
        return settings;
    }

    public Map<String, String> getDocument(String docId, String username) {
        Map<String, String> document = new LinkedHashMap<>();
        document.put("status", "success");
        document.put("documentId", docId);
        document.put("content", "Highly confidential information for " + docId);
        document.put("accessedBy", username);
        document.put("message", "You have successfully passed the ABAC check (must be the resource owner OR an ADMIN).");
        return document;
    }
}
