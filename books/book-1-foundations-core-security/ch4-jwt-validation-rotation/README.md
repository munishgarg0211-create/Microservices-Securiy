# ch4 - JWT Validation and Rotation

- Book: book-1-foundations-core-security
- Chapter: ch4
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, Spring Security OAuth2 Resource Server

## Objective
Validate JWT and rotate signing keys safely. 
Demonstrate proper `BOLA` (Broken Object Level Authorization) prevention by reading user identity correctly from a securely validated cryptographic token rather than untrusted client input.

## Mitigation Logic
- Control family: `AUTHZ` (identity and object-level authorization enforcement).
- Core secure/insecure decision model in code:
  - Secure mode (`/api/demo/secure`) requires a valid OAuth2 Bearer token (JWT). It ensures the subject inside the JWT (`actor`) is verified by a trusted Identity Provider, preventing impersonation. It then checks ownership before allowing access.
  - Insecure mode (`/api/demo/insecure`) bypasses proper authentication and trusts HTTP query parameters (`?actor=alice&owner=bob`), demonstrating a BOLA/IDOR exploit scenario.

- Good practice (`/api/demo/secure`):
  - Forces clients to provide an `Authorization: Bearer <jwt-token>` header.
  - Checks if `jwt.getSubject()` matches the required `owner` (or if the token contains an `admin` scope).

- Bad practice (`/api/demo/insecure`):
  - Requires no token. Allows anyone to pass `actor=eve` and gain access to `owner=bob`'s resources.

## Built-In Key Rotation
This demo leverages Spring Security's native `oauth2ResourceServer().jwt()` configuration in `SecurityConfig.java`. Key rotation is handled seamlessly:
1. `application.yml` maps `spring.security.oauth2.resourceserver.jwt.jwk-set-uri` to the IdP's JWKS endpoint (e.g. `http://localhost:9000/oauth2/jwks`).
2. Spring Boot automatically creates a `JwtDecoder` that fetches and caches the public keys from this URL.
3. If an Identity Provider rotates its keys, Spring Security reacts to the `Cache-Control` header or invalid key identifiers (kid) and refreshes the key set automatically, ensuring zero-downtime key rotation.

## Run Plan
1. Start service: `mvn spring-boot:run`. Note: To test the `/secure` endpoint interactively, you would need a valid JWT issued by the IdP specified in your `application.yml`.
2. Run insecure path: `GET /api/demo/insecure?actor=eve&owner=bob` and observe the simulated BOLA vulnerability.
3. Observe tests: Run `mvn test` to see how Spring Security's `MockMvcRequestPostProcessors.jwt()` allows developers to easily mock JWTs for robust unit and integration testing.

## Code Demonstration Map

<!-- CODE_MAP_START -->
- `src/main/java/.../DemoApplication.java`: Spring Boot entrypoint.
- `src/main/java/.../SecurityConfig.java`: Configures the OAuth2 Resource server and API route security checks.
- `src/main/java/.../DemoController.java`: API layer extracting identity strictly via `@AuthenticationPrincipal Jwt jwt`.
- `src/main/java/.../DemoService.java`: Service layer enforcing ownership logic.
- `src/main/resources/application.yml`: Configuration file mapping the Identity Provider JWKS URI.
- `src/test/java/.../DemoControllerTest.java`: Integration tests using robust `@WithMockJwt` flows to ensure APIs are locked down properly.
- `src/test/java/.../DemoServiceTest.java`: Unit tests validating authorization domain logic.
<!-- CODE_MAP_END -->

## Quick Start
- Build & Test: `mvn clean test`
