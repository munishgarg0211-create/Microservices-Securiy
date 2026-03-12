package com.munishgarg.microsecurity.book1.ch4_oauth2_grants_demo;

import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    // 1. Authorization Code Flow Example
    // This endpoint represents a page a user visits. Spring Security intercepts the request,
    // redirects the user to login, and then extracts their profile into the OAuth2User.
    @GetMapping("/auth-code")
    public Map<String, Object> getAuthorizationCodeDemo(@AuthenticationPrincipal OAuth2User oauth2User) {
        String username = oauth2User != null ? oauth2User.getName() : "anonymous";
        return demoService.demoAuthorizationCodeFlow(username);
    }

    // 2. Client Credentials Flow Example
    // This endpoint demonstrates the server retrieving a token for ITSELF to call another API.
    // Notice it doesn't need an @AuthenticationPrincipal because it's a machine-to-machine flow.
    @GetMapping("/client-credentials")
    public Map<String, Object> getClientCredentialsDemo(
            @RegisteredOAuth2AuthorizedClient("backend-service") OAuth2AuthorizedClient authorizedClient) {
        
        // This token was retrieved securely in the background using the client id and secret.
        String token = authorizedClient.getAccessToken().getTokenValue();
        return demoService.demoClientCredentialsFlow(token);
    }
}
