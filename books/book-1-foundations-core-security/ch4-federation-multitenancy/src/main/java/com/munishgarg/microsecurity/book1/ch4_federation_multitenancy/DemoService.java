package com.munishgarg.microsecurity.book1.ch4_federation_multitenancy;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    public Map<String, String> getTenantData(String tenantId, String username) {
        Map<String, String> data = new LinkedHashMap<>();
        data.put("status", "success");
        data.put("tenant", tenantId);
        data.put("accessedBy", username);
        data.put("message", "You successfully accessed isolated data for tenant: " + tenantId);
        data.put("securityMechanism", "JwtAuthenticationConverter extracted tenant_id claim");
        return data;
    }

    public Map<String, String> getSystemHealth(String username) {
        Map<String, String> data = new LinkedHashMap<>();
        data.put("status", "healthy");
        data.put("accessedBy", username);
        data.put("message", "Global system health metrics accessed.");
        data.put("securityMechanism", "Standard RBAC hasRole('ADMIN')");
        return data;
    }
}
