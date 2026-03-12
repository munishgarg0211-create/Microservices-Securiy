package com.munishgarg.microsecurity.book1.ch4_oauth2_grants_demo;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DemoService {

    private static final String PROJECT = "ch4-oauth2-grants-demo";
    private static final String BOOK = "book-1-foundations-core-security";
    private static final String OBJECTIVE = "Compare client credentials and auth code flows.";
    private static final String CONCEPT = "Oauth2 Grants Demo";
    private static final String CONTROL_FAMILY = "AUTHZ";

    private final WebClient webClient;

    public DemoService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Map<String, Object> demoAuthorizationCodeFlow(String username) {
        Map<String, Object> result = buildBaseResult("authorization_code");
        result.put("actor", username);
        result.put("scenario", "user-interactive-login");
        result.put("controlDecision", "allow");
        result.put("expectedBehavior", "User redirects to Identity Provider, logs in, and the application receives an authorization code and exchanges it for a token on behalf of " + username);
        result.put("riskScore", 25);
        
        return result;
    }

    public Map<String, Object> demoClientCredentialsFlow(String tokenValue) {
        Map<String, Object> result = buildBaseResult("client_credentials");
        result.put("actor", "book-demo-service"); // The application itself is the actor
        result.put("scenario", "machine-to-machine");
        
        // In a real application, the WebClient injected in this service 
        // will automatically append "Authorization: Bearer <token>" to downstream requests.
        // Example: webClient.get().uri("http://downstream-api/data").retrieve().bodyToMono(String.class);
        
        result.put("downstreamTokenPrefix", tokenValue.substring(0, Math.min(tokenValue.length(), 10)) + "...");
        result.put("controlDecision", "allow");
        result.put("expectedBehavior", "The microservice acts independently. It fetches a token using its secure client credentials and calls backend APIs without requiring a user in the loop.");
        result.put("riskScore", 15); // Lower risk because no user passwords are involved, but high privilege if compromised.

        return result;
    }

    private Map<String, Object> buildBaseResult(String grantType) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("project", PROJECT);
        result.put("book", BOOK);
        result.put("status", "sample-ready");
        result.put("grantType", grantType);
        result.put("concept", CONCEPT);
        result.put("objective", OBJECTIVE);
        result.put("controlFamily", CONTROL_FAMILY);
        return result;
    }
}
