# ch5 - Gateway Role Demo

- Book: book-1-foundations-core-security
- Chapter: ch5
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, Spring Cloud Gateway, Spring Security (Reactive)

## Objective
Demonstrate the core architectural role of an API Gateway acting as a network perimeter edge. The Gateway shields backend microservices by centralizing cross-cutting security concerns such as request routing, mutation, and authentication.

## Security Controls Demonstrated
- Control family: `ARCHITECTURE` (Security By Design)
- **Centralized Edge Authentication:**
  - *Anti-Pattern:* Every individual microservice independently manages user sessions and credential verification, leading to varied implementation quality, duplicated effort, and a widened attack surface.
  - *Secure Fix:* Deploying a single API Gateway (like Spring Cloud Gateway configured with `spring-boot-starter-security`) at the network perimeter. All external traffic must pass through this Gateway. If a request is unauthenticated (`401`), the Gateway drops it *before* it ever reaches the internal network.
- **Request Mutation / Header Injection:**
  - *Pattern:* The Gateway inspects incoming traffic, authenticates it, and then explicitly mutates the request (e.g., via the `AddRequestHeader` filter in `application.yml`) before routing it downstream. This allows downstream services to implicitly trust that requests bearing specific gateway signatures have been scrubbed.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/resources/application.yml`: Defines the declarative routing table. Forwards `/api/backend/**` to a mock backend while injecting a `X-Gateway-Routed: true` header.
- `src/main/java/.../SecurityConfig.java`: Configures Spring WebFlux Security to enforce authentication globally at the Gateway level using a `SecurityWebFilterChain`.
- `src/main/java/.../DemoBackendController.java`: A mock downstream microservice. Notice it has *zero* security annotations. It relies entirely on the Gateway's perimeter defense.
- `src/test/java/.../GatewaySecurityTest.java`: Uses `WebTestClient` to prove that unauthenticated external traffic is blocked, while authenticated traffic is correctly routed and mutated by the Gateway's filters.
<!-- CODE_MAP_END -->

## Quick Start
You can build and test this project via Maven. The JUnit integration tests perfectly illustrate the Gateway's behavior.
- `mvn clean test`
