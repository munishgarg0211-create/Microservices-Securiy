package com.munishgarg.microsecurity.book1.ch4_auth_anti_patterns_lab;

import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lab")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    // ============================================
    // VULNERABILITY 1: Broken Object Level Auth (BOLA / IDOR)
    // ============================================
    
    /**
     * ANTI-PATTERN: Retrieves data based on the requested ID without checking if the 
     * currently authenticated user is actually the owner of that data.
     * Alice can request Bob's data just by changing the URL /api/lab/vulnerable/data/bob.
     */
    @GetMapping("/vulnerable/data/{userId}")
    public Map<String, String> getVulnerableData(@PathVariable String userId) {
        return demoService.getUserData(userId);
    }

    /**
     * SECURE: Enforces that the requested `userId` matches the authenticated principal's name.
     * Alice cannot fetch Bob's data because the PreAuthorize expression will evaluate to false.
     */
    @PreAuthorize("#userId == authentication.name")
    @GetMapping("/secure/data/{userId}")
    public Map<String, String> getSecureData(@PathVariable String userId) {
        return demoService.getUserData(userId);
    }


    // ============================================
    // VULNERABILITY 2: Missing Function Level Access Control
    // ============================================

    /**
     * ANTI-PATTERN: An administrative function exposed without role checks.
     * A developer might assume that because the UI hides the "Admin Panel" button from 
     * normal users, the endpoint is safe. An attacker can just guess the URL.
     */
    @GetMapping("/vulnerable/admin/settings")
    public Map<String, String> getVulnerableAdminSettings() {
        return demoService.getAdminSettings();
    }

    /**
     * SECURE: Explicitly declares that only principals with the ADMIN role can execute this.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/secure/admin/settings")
    public Map<String, String> getSecureAdminSettings() {
        return demoService.getAdminSettings();
    }
}
