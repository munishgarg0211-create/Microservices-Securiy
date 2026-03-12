package com.munishgarg.microsecurity.book1.ch4_jwt_validation_rotation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.Jwt;

class DemoServiceTest {

    private final DemoService service = new DemoService();

    private Jwt buildJwt(String subject, List<String> scopes) {
        Jwt.Builder builder = Jwt.withTokenValue("mock-token-value")
                .header("alg", "RS256")
                .header("typ", "JWT")
                .claim("sub", subject)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600));
                
        if (scopes != null && !scopes.isEmpty()) {
            builder.claim("scope", scopes);
        }
        
        return builder.build();
    }

    @Test
    void shouldAllowAccessWhenActorMatchesOwner() {
        Jwt jwt = buildJwt("bob", null);

        Map<String, Object> result = service.demoSecure(jwt, "bob");
        
        assertEquals("secure", result.get("mode"));
        assertEquals("bob", result.get("actor"));
        assertEquals("bob", result.get("resourceOwner"));
        assertEquals("allow", result.get("controlDecision"));
    }

    @Test
    void shouldAllowAccessWhenActorIsAdmin() {
        Jwt jwt = buildJwt("admin-user", List.of("admin"));

        Map<String, Object> result = service.demoSecure(jwt, "bob");
        
        assertEquals("secure", result.get("mode"));
        assertEquals("admin-user", result.get("actor"));
        assertEquals("allow", result.get("controlDecision"));
    }

    @Test
    void shouldDenyAccessWhenActorDoesNotMatchOwnerAndNotAdmin() {
        Jwt jwt = buildJwt("eve", null);
        
        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () -> {
            service.demoSecure(jwt, "bob");
        });
        
        assertNotNull(exception);
    }

    @Test
    void shouldDemonstrateInsecureBolaVulnerability() {
        Map<String, Object> result = service.demoInsecure("eve", "bob");
        
        assertEquals("insecure", result.get("mode"));
        assertEquals("eve", result.get("actor"));
        assertEquals("allow", result.get("controlDecision"));
        assertEquals(95, result.get("riskScore"));
    }
}
