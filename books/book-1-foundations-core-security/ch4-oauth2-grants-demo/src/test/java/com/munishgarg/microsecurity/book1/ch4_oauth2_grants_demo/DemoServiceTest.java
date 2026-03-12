package com.munishgarg.microsecurity.book1.ch4_oauth2_grants_demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

class DemoServiceTest {

    private final WebClient webClient = mock(WebClient.class);
    private final DemoService service = new DemoService(webClient);

    @Test
    void shouldReturnAuthCodeResult() {
        Map<String, Object> result = service.demoAuthorizationCodeFlow("alice");

        assertNotNull(result);
        assertEquals("authorization_code", result.get("grantType"));
        assertEquals("alice", result.get("actor"));
        assertEquals("user-interactive-login", result.get("scenario"));
        assertEquals(25, result.get("riskScore"));
    }

    @Test
    void shouldReturnClientCredentialsResult() {
        Map<String, Object> result = service.demoClientCredentialsFlow("super-secret-system-token");

        assertEquals("client_credentials", result.get("grantType"));
        assertEquals("book-demo-service", result.get("actor"));
        assertEquals("machine-to-machine", result.get("scenario"));
        assertEquals(15, result.get("riskScore"));
        
        String prefix = (String) result.get("downstreamTokenPrefix");
        assertTrue(prefix.startsWith("super-secr"));
    }
}
