package com.munishgarg.microsecurity.book1.ch4_jwt_validation_rotation;

import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    // Production copy/paste checklist:
    // 1) Read actor from authenticated principal/claims (JWT header), never from request params.
    // 2) Keep authorization/business decisions in service/policy layer or via @PreAuthorize, not hardcoded in controllers.

    @GetMapping("/secure")
    public Map<String, Object> getSecureDemo(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(defaultValue = "bob") String owner) {
        // We pass the secure JWT claims down to the service for object-level authorization (BOLA prevention)
        return demoService.demoSecure(jwt, owner);
    }

    @GetMapping("/insecure")
    public Map<String, Object> getInsecureDemo(
            @RequestParam(defaultValue = "alice") String actor,
            @RequestParam(defaultValue = "bob") String owner) {
        // Vulnerable: Relies on unvalidated query parameters for identity
        return demoService.demoInsecure(actor, owner);
    }
}
