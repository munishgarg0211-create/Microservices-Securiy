# ch3 - HTTPS Hardening

- Book: book-1-foundations-core-security
- Chapter: ch3
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, JUnit 5

## Objective
Demonstrate secure transport hardening by enforcing modern TLS versions (1.2+) and rejecting deprecated protocols (SSLv3, TLS 1.0/1.1) to prevent downgrade and interception attacks.

## Secure Implementation Logic
- **Control Family:** `TRANSPORT` (Transport Layer Security).
- **Core Principle:** Only cryptographic protocols and cipher suites currently deemed secure by industry standards (NIST, OWASP) should be accepted for service communication.
- **Implementation:**
    - The `DemoService` simulates a TLS handshake filter.
    - It validates the `tlsVersion` parameter against a secure baseline (TLS 1.2 or 1.3).
    - Attempts to connect using legacy protocols are denied, ensuring that the transport channel remains robust against known vulnerabilities.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoController.java`: API entry point that accepts a `tlsVersion` parameter to simulate connection attempts.
- `src/main/java/.../DemoService.java`: Contains the core protocol validation logic for transport hardening.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying that TLS 1.2+ is allowed while TLS 1.0/1.1 is rejected.
- `src/test/java/.../DemoServiceTest.java`: Unit tests for the TLS version validation logic.
<!-- CODE_MAP_END -->

## Quick Start
1. Build and Test: `mvn clean test`
2. Run locally: `mvn spring-boot:run`
3. Verify via Curl:
    - Success: `curl "http://localhost:8080/api/demo?tlsVersion=1.2"`
    - Deny: `curl "http://localhost:8080/api/demo?tlsVersion=1.0"`

## Acceptance Criteria
- Connection attempts using modern TLS (1.2/1.3) resulting in a `controlDecision: allow`.
- Connection attempts using deprecated TLS (1.0/1.1) resulting in a `controlDecision: deny`.
- Response payloads include standard metadata (`riskScore`, `controlFamily`, `concept`).
