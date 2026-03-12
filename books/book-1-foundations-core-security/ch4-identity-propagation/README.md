# ch4 - Identity Propagation Demo

- Book: book-1-foundations-core-security
- Chapter: ch4
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, Spring WebFlux, Spring Security (OAuth2 Resource Server)

## Objective
Demonstrate how to securely propagate user identity (context passing) across a chain of microservices without resorting to insecure, spoofable custom headers. This project illustrates intercepting outgoing HTTP requests and automatically attaching the current verified JWT Bearer token using Spring WebFlux's `ServletBearerExchangeFilterFunction`.

## Mitigation Logic
- Control family: `AUTHZ` (Authorization and Access Control) / Identity Propagation
- **Good practice:** When Service A calls Service B on behalf of a user, it must pass a cryptographically verifiable token (JWT) representing that user.
- **Bad practice:** Using an unverified header like `X-User-Id: alice` which can be easily spoofed if an attacker accesses the internal network.

## Key Concepts Demonstrated
1. **WebClient Filter (`ServletBearerExchangeFilterFunction`)**: A Spring component that automatically extracts the OAuth2 token from the current `SecurityContext` and inserts it into the `Authorization: Bearer` header of outgoing requests.
2. **Zero-Trust Downstream**: The downstream endpoint (`/internal/downstream-api/profile`) is completely stateless and verifies the JWT independently, ensuring the identity is genuine.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoApplication.java`: Spring Boot entrypoint.
- `src/main/java/.../SecurityConfig.java`: Configures the app as an OAuth2 Resource Server and wires in the `ServletBearerExchangeFilterFunction` to the `WebClient` bean.
- `src/main/java/.../DemoController.java`: Simulates an external entrypoint and an internal downstream API.
- `src/main/java/.../DemoService.java`: Service layer demonstrating the WebClient call without manually handling tokens.
- `src/test/java/.../DemoServiceTest.java`: Unit tests confirming WebClient mock behavior and Security Context identity extraction.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying strict authentication requirements using standard `SecurityMockMvcRequestPostProcessors.jwt()` testing utilities.
<!-- CODE_MAP_END -->

## Quick Start
You can build and test this project via Maven. 
- `mvn clean test`
