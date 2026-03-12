# ch4 - Token Validation Strategies Demo

- Book: book-1-foundations-core-security
- Chapter: ch4
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, Spring Security (OAuth2 Resource Server)

## Objective
Demonstrate advanced JWT validation strategies beyond basic signature verification. This project shows how to construct a custom `OAuth2TokenValidator` to mitigate the "Confused Deputy" vulnerability by strictly enforcing the token's `aud` (Audience) claim.

## Mitigation Logic
- Control family: `AUTHZ` (Authorization and Access Control) / Token Validation
- **Good practice:** When accepting a JWT, always verify that your specific microservice is listed as the intended audience (`aud`).
- **Bad practice:** Checking only the cryptographic signature. If Service A and Service B both trust the same IdP, a token minted for A could be maliciously submitted to B. If B doesn't check the audience, it acts as a confused deputy and processes the request.

## Key Concepts Demonstrated
1. **Custom `JwtDecoder` Configuration**: Wiring a `NimbusJwtDecoder` manually in `SecurityConfig.java` to inject custom validation logic into the Spring Security pipeline.
2. **`AudienceValidator`**: A custom implementation of `OAuth2TokenValidator` that explicitly rejects tokens missing the `api://demo-service` audience string.
3. **Delegating Validators**: Combining default validation (Expiration, Issuer) with our custom Audience Validation using `DelegatingOAuth2TokenValidator`.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoApplication.java`: Spring Boot entrypoint.
- `src/main/java/.../SecurityConfig.java`: Configures the Resource Server and manual `JwtDecoder` Bean containing the delegating validators.
- `src/main/java/.../AudienceValidator.java`: The core logic that inspects the JWT claims and fails the Authentication process if the audience doesn't match.
- `src/main/java/.../DemoController.java`: A basic endpoint secured by the underlying token validation stack.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying that tokens with mismatched Audiences or rogue Issuers are explicitly rejected with `401 Unauthorized`.
<!-- CODE_MAP_END -->

## Quick Start
You can build and test this project via Maven. The JUnit integration tests perfectly illustrate the security matrix across different mock JWTs.
- `mvn clean test`
