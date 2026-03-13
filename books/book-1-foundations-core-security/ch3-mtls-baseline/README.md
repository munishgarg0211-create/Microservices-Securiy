# ch3 - mTLS Baseline

- Book: book-1-foundations-core-security
- Chapter: ch3
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, JUnit 5

## Objective
Demonstrate secure implementation of mutual TLS (mTLS) for machine-to-machine (M2M) identity verification, ensuring both client and server are cryptographically authenticated.

## Secure Implementation Logic
- **Control Family:** `TRANSPORT` (Transport Layer Security).
- **Core Principle:** In zero-trust architectures, every service-to-service connection must require a valid, trusted client certificate to establish identity and prevent unauthorized lateral movement.
- **Implementation:**
    - The `DemoService` simulates an mTLS handshake verification.
    - It enforces both the presence of a `clientCertPresent` and that the certificate is `certTrusted` by the server's truststore.
    - If either condition is not met, the connection is denied, providing a robust second layer of defense beyond traditional authentication.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoController.java`: API entry point that accepts `clientCertPresent` and `certTrusted` parameters to simulate mTLS handshake scenarios.
- `src/main/java/.../DemoService.java`: Contains the core verification logic for mTLS identity.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying that only trusted mTLS connections are allowed.
- `src/test/java/.../DemoServiceTest.java`: Unit tests for the mTLS validation logic.
<!-- CODE_MAP_END -->

## Quick Start
1. Build and Test: `mvn clean test`
2. Run locally: `mvn spring-boot:run`
3. Verify via Curl:
    - Success: `curl "http://localhost:8080/api/demo?clientCertPresent=true&certTrusted=true"`
    - Deny: `curl "http://localhost:8080/api/demo?clientCertPresent=true&certTrusted=false"`

## Acceptance Criteria
- mTLS handshakes missing a certificate or using an untrusted one resulting in a `controlDecision: deny`.
- Fully verified mTLS connections resulting in a `controlDecision: allow`.
- Response payloads include standard metadata (`riskScore`, `controlFamily`, `concept`).
