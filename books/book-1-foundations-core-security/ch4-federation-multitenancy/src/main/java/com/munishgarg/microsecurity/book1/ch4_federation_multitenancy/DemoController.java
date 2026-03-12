package com.munishgarg.microsecurity.book1.ch4_federation_multitenancy;

import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    // Tenant Isolation
    // Enforces that the JWT must contain the specific tenant_id matching the path variable.
    // E.g., if path is 'acme', user must have 'ROLE_TENANT_ACME'.
    @GetMapping("/tenant/{tenantId}/data")
    @PreAuthorize("hasRole('TENANT_' + #tenantId.toUpperCase())")
    public Map<String, String> getTenantData(
            @PathVariable("tenantId") String tenantId, 
            Authentication authentication) {
        
        return demoService.getTenantData(tenantId, authentication.getName());
    }

    // Standard RBAC
    // Allows us to still use standard roles alongside our custom tenant logic.
    @GetMapping("/system/health")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, String> getSystemHealth(Authentication authentication) {
        return demoService.getSystemHealth(authentication.getName());
    }
}
