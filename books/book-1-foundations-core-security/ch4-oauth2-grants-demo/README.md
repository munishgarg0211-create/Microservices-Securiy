# ch4 - OAuth2 Grants Demo

- Book: book-1-foundations-core-security
- Chapter: ch4
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, Spring Security OAuth2 Client, WebFlux

## Objective
Compare and contrast the two most common OAuth2 workflows in a microservices environment:
1. **Authorization Code Grant:** Best practice for authenticating a human user (actor) through an interactive login page.
2. **Client Credentials Grant:** Best practice for machine-to-machine communication, where a microservice retrieves its own token to call a backend API.

## Mitigation Logic
- Control family: `AUTHZ` (Token lifecycle and security contexts).
- **Good practice:** Select the grant type that perfectly matches the interaction type. 
  - Using a Client Credentials grant on a frontend SPA is insecure (exposes the secret).
  - Using an Authorization Code grant for backend sync workflows is impossible (requires a browser and human click).

## Built-In Flow Management
This demo leverages Spring Security's native `oauth2Login()` and `oauth2Client()` configuration in `SecurityConfig.java`. 

### Authorization Code
1. A user accesses `/api/demo/auth-code` in a browser.
2. Spring intercepts the request because they are not authenticated, generating a completely secure redirect URL to the Identity Provider (using PCKE by default).
3. The user logs in at the IdP, and is redirected back with a `code`.
4. Spring exchanges the `code` for an access token entirely on the backend, ensuring the browser never sees the raw token.
5. `DemoController.java` reads the profile via `@AuthenticationPrincipal OAuth2User`.

### Client Credentials
1. `WebClientConfig.java` configures a reactive `WebClient` using a `ServletOAuth2AuthorizedClientExchangeFilterFunction`.
2. A scheduled task or backend endpoint calls a method triggering this `WebClient`.
3. Spring Security sees the request is configured to use the `backend-service` client-registration.
4. If there is no cached token, Spring transparently calls the IdP's `/oauth2/token` endpoint with the ID and Secret.
5. Spring appends `Authorization: Bearer <the-new-token>` to the HTTP request and transmits it to the destination API.

## Code Demonstration Map

<!-- CODE_MAP_START -->
- `src/main/java/.../DemoApplication.java`: Spring Boot entrypoint.
- `src/main/java/.../SecurityConfig.java`: Enables `oauth2Login()` for humans and `oauth2Client()` for background token management.
- `src/main/java/.../WebClientConfig.java`: Configures the `WebClient` to automatically append Client Credential tokens to outgoing requests.
- `src/main/java/.../DemoController.java`: Two endpoints demonstrating the distinct ways OAuth2 contexts are accessed (`@AuthenticationPrincipal` vs `@RegisteredOAuth2AuthorizedClient`).
- `src/main/java/.../DemoService.java`: Evaluates the output of both grants.
- `src/main/resources/application.yml`: Configuration file mapping the Identity Provider URIs for the `webapp-client` (auth code) and `backend-service` (client credentials).
- `src/test/java/.../DemoControllerTest.java`: Integration tests using `MockMvc` to verify anonymous redirects to the login screen.
- `src/test/java/.../DemoServiceTest.java`: Unit tests validating authorization domain logic.
<!-- CODE_MAP_END -->

## Quick Start
- Build & Test: `mvn clean test`
