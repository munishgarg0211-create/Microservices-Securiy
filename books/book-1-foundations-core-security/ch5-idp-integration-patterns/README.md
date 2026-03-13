# ch5 - Identity Provider Integration Patterns

- Book: book-1-foundations-core-security
- Chapter: ch5
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, Spring Security (OAuth2 Client)

## Objective
Demonstrate the "Delegitimizing Custom Passwords" pattern by completely outsourcing user authentication to external, dedicated Identity Providers (IdPs) like Google, Okta, or Keycloak via OpenID Connect (OIDC).

## Security Controls Demonstrated
- Control family: `AUTHN` (Authentication)
- **Delegated Identity (OAuth2/OIDC):**
  - *Anti-Pattern:* A microservice creates its own `users` table, hashes passwords (even with strong algorithms like bcrypt), and implements custom login screens and forgotten password flows. This creates a massive liability.
  - *Secure Fix:* The application holds *zero* credentials. It uses `spring-boot-starter-oauth2-client` to redirect unauthenticated users to a trusted IdP. Upon successful login at the IdP, the application receives an OIDC token containing verified identity claims (like email and name) and establishes the local session securely.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/resources/application.yml`: Shows how to register external IdPs. It includes a mock Google configuration and an enterprise Keycloak configuration, defining properties like `client-id` and `client-secret`.
- `src/main/java/.../SecurityConfig.java`: The core security configuration. Notice it uses `oauth2Login()` and *does not* implement a `UserDetailsService`.
- `src/main/java/.../DemoController.java`: Demonstrates how to access the federated user's profile data through the `@AuthenticationPrincipal OidcUser` object provided by Spring Security after a successful OAuth2 flow.
- `src/test/java/.../DemoControllerTest.java`: Uses `SecurityMockMvcRequestPostProcessors.oidcLogin()` to prove that the application correctly handles unauthenticated redirects and successfully parses OIDC tokens into usable profiles.
<!-- CODE_MAP_END -->

## Quick Start
You can build and test this project via Maven. The JUnit integration tests perfectly illustrate the OIDC flow.
- `mvn clean test`
