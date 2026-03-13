# ch1 - Role-Based Learning Labs

- Book: book-1-foundations-core-security
- Chapter: ch1
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, JUnit 5

## Objective
Demonstrate secure implementation of Role-Based Access Control (RBAC) by defining granular permissions for different engineering personas (Developers, Security Engineers, SREs).

## Secure Implementation Logic
- **Control Family:** `AUTHZ` (Granular Access Control and Identity).
- **Core Principle:** Least Privilege. Every user or automated service should only possess the minimum permissions necessary to complete their assigned task. This project codifies these permissions to prevent unauthorized horizontal or vertical movement.
- **Implementation:**
    - The `DemoService` acts as a role-action enforcement point.
    - It maintains a strict mapping of `role` to `action`.
    - Developers are restricted to code-related tasks.
    - Security personas are restricted to auditing and vulnerability management.
    - SRE/Operations are restricted to deployment and observability.
    - Any attempt to cross these boundaries results in an immediate authorization failure.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoController.java`: API entry point that accepts `role` and `action` parameters to simulate RBAC enforcement scenarios.
- `src/main/java/.../DemoService.java`: Contains the core RBAC logic and role-to-action mappings.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying authorized and unauthorized role-action combinations.
- `src/test/java/.../DemoServiceTest.java`: Unit tests for the RBAC enforcement logic.
<!-- CODE_MAP_END -->

## Quick Start
1. Build and Test: `mvn clean test`
2. Run locally: `mvn spring-boot:run`
3. Verify via Curl:
    - Success (Authorized): `curl "http://localhost:8080/api/demo?role=Developer&action=write-code"`
    - Success (Denied): `curl "http://localhost:8080/api/demo?role=Developer&action=deploy-service"`

## Acceptance Criteria
- Authorized role-action pairs resulting in `controlDecision: allow`.
- Unauthorized role-action pairs resulting in `controlDecision: deny` and a high risk score.
- Response payloads include standard metadata (`riskScore`, `controlFamily`, `concept`).
