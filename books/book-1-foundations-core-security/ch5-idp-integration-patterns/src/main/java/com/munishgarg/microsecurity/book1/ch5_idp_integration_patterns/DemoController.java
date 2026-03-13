package com.munishgarg.microsecurity.book1.ch5_idp_integration_patterns;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    /**
     * This endpoint demonstrates extracting federated identity data directly from the
     * OIDC token provided by the external IdP (e.g., Google or Keycloak).
     * Notice we don't query a local `users` database.
     */
    @GetMapping("/me")
    public Map<String, Object> getProfile(@AuthenticationPrincipal OidcUser principal) {
        Map<String, Object> profile = new LinkedHashMap<>();
        
        // Defensive check: In a real app with pure oauth2Login, this shouldn't be null
        // if the request reached the controller securely.
        if (principal == null) {
            profile.put("error", "No OIDC user found in security context.");
            return profile;
        }

        profile.put("status", "success");
        profile.put("message", "User identity successfully established via Identity Provider delegation.");
        
        // Extracting standardized OIDC claims
        profile.put("subject", principal.getSubject());
        profile.put("fullName", principal.getFullName());
        profile.put("email", principal.getEmail());
        
        return profile;
    }
}
