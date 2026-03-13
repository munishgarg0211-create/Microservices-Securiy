# ch3 - Kafka Security Baseline

- Book: book-1-foundations-core-security
- Chapter: ch3
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, JUnit 5, Kafka (Mocked logic)

## Objective
Demonstrate a production-grade secure Kafka implementation by enforcing encrypted transport (SSL/TLS), strong identity (SASL), and granular resource permissions (ACLs).

## Secure Implementation Logic
- **Control Family:** `MESSAGING` (Event Bus and Message Security).
- **Core Principle:** An event bus is a high-value target for lateral movement and data exfiltration. Security must be multi-layered: encrypting data in transit, authenticating every client, and authorizing every produce/consume operation.
- **Implementation:**
    - The `DemoService` simulates the triple-check security model of a hardened Kafka cluster.
    - It verifies `sslEnabled` for encryption.
    - It verifies `saslAuthenticated` for machine identity.
    - It verifies `aclAuthorized` for specific topic-level permissions.
    - This "defense-in-depth" approach ensures that even if one layer is compromised, the others continue to protect the messaging backbone.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoController.java`: API entry point that accepts `sslEnabled`, `saslAuthenticated`, and `aclAuthorized` parameters to simulate Kafka connection states.
- `src/main/java/.../DemoService.java`: Contains the core Kafka security verification logic.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying that all security layers must be active for successful interaction.
- `src/test/java/.../DemoServiceTest.java`: Unit tests for the multi-layered security model.
<!-- CODE_MAP_END -->

## Quick Start
1. Build and Test: `mvn clean test`
2. Run locally: `mvn spring-boot:run`
3. Verify via Curl:
    - Success: `curl "http://localhost:8080/api/demo?sslEnabled=true&saslAuthenticated=true&aclAuthorized=true"`
    - Deny: `curl "http://localhost:8080/api/demo?sslEnabled=true&saslAuthenticated=true&aclAuthorized=false"`

## Acceptance Criteria
- Kafka interactions missing ANY of the three security layers (SSL, SASL, ACL) resulting in a `controlDecision: deny`.
- Fully secured Kafka connections resulting in a `controlDecision: allow`.
- Response payloads include standard metadata (`riskScore`, `controlFamily`, `concept`).
