# ch3 - gRPC TLS and Authz

- Book: book-1-foundations-core-security
- Chapter: ch3
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, JUnit 5, gRPC (Mocked logic)

## Objective
Demonstrate secure gRPC communication by enforcing transport layer security (TLS) for encryption and call-level metadata (tokens) for fine-grained authorization of remote procedure calls.

## Secure Implementation Logic
- **Control Family:** `TRANSPORT` (Transport Layer Security and Channel Security).
- **Core Principle:** gRPC security should not rely solely on network-level controls. It must use end-to-end TLS for privacy and per-call credentials (like JWTs in metadata) to ensure every operation is authorized.
- **Implementation:**
    - The `DemoService` simulates the gRPC interceptor logic.
    - It verifies that the channel has `tlsEnabled`.
    - It further verifies that the call metadata contains a `callTokenValid` credential.
    - If either transport or call-level security is missing, the request is rejected, shielding the internal service logic from unauthorized exposure.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoController.java`: API entry point that accepts `tlsEnabled` and `callTokenValid` parameters to simulate secure gRPC invocation scenarios.
- `src/main/java/.../DemoService.java`: Contains the core gRPC security validation logic.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying that secure gRPC calls require both TLS and valid tokens.
- `src/test/java/.../DemoServiceTest.java`: Unit tests for the gRPC security logic.
<!-- CODE_MAP_END -->

## Quick Start
1. Build and Test: `mvn clean test`
2. Run locally: `mvn spring-boot:run`
3. Verify via Curl:
    - Success: `curl "http://localhost:8080/api/demo?tlsEnabled=true&callTokenValid=true"`
    - Deny: `curl "http://localhost:8080/api/demo?tlsEnabled=true&callTokenValid=false"`

## Acceptance Criteria
- gRPC invocations missing TLS OR missing valid call credentials resulting in a `controlDecision: deny`.
- Fully secured gRPC calls resulting in a `controlDecision: allow`.
- Response payloads include standard metadata (`riskScore`, `controlFamily`, `concept`).
