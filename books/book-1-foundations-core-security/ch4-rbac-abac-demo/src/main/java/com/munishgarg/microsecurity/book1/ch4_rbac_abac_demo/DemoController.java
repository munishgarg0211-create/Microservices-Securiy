package com.munishgarg.microsecurity.book1.ch4_rbac_abac_demo;

import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    // Role-Based Access Control (RBAC) - Requires the ADMIN authority
    // The ROLE_ prefix is handled automatically by hasRole in Spring Security
    @GetMapping("/admin-settings")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, String> getAdminSettings(@AuthenticationPrincipal UserDetails userDetails) {
        return demoService.getAdminSettings(userDetails.getUsername());
    }

    // Attribute-Based Access Control (ABAC) - Requires ownership of the specifically requested resource.
    // We call an external Bean 'docSecurity' and pass the method argument and authentication context.
    // Also allows admins to bypass ownership checks.
    @GetMapping("/documents/{docId}")
    @PreAuthorize("hasRole('ADMIN') or @docSecurity.isOwner(#docId, authentication.name)")
    public Map<String, String> getDocument(
            @PathVariable("docId") String docId, 
            @AuthenticationPrincipal UserDetails userDetails) {
        
        return demoService.getDocument(docId, userDetails.getUsername());
    }
}
