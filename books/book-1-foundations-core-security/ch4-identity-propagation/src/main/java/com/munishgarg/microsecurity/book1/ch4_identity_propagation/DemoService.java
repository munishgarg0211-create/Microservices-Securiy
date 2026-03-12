package com.munishgarg.microsecurity.book1.ch4_identity_propagation;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class DemoService {

    private final WebClient webClient;

    public DemoService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Uses WebClient to call the mocked downstream API.
     * Notice there is NO CODE manipulating headers or manually extracting the JWT.
     * It is automatically handled by the ServletBearerExchangeFilterFunction configured in SecurityConfig.
     */
    public Mono<Map<String, String>> fetchDownstreamProfile() {
        return webClient.get()
                // In a real scenario, this would be http://downstream-service:8080/api...
                // Using localhost for the self-contained mocked demo
                .uri("http://localhost:8080/api/demo/internal/downstream-api/profile")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {})
                .map(response -> {
                    response.put("serviceChain", "Frontend-API -> Downstream-API");
                    response.put("securityMechanism", "Automatically Propagated Bearer Token via WebClient Filter");
                    return response;
                });
    }

    /**
     * Mock data returned by the 'downstream' service.
     * Proves that the SecurityContext (Identity) survived the HTTP hop.
     */
    public Map<String, String> getMockedDownstreamData() {
        Map<String, String> data = new LinkedHashMap<>();
        
        // Grab the identity that Spring Security parsed from the propagated JWT Token
        String principalName = SecurityContextHolder.getContext().getAuthentication().getName();
        
        data.put("status", "success");
        data.put("downstreamData", "Sensitive profile details");
        data.put("authenticatedUserAtDownstream", principalName);
        return data;
    }
}
