# ch2 - OWASP BOLA Lab

- Book: book-1-foundations-core-security
- Chapter: ch2
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, JUnit 5

## Objective
Demonstrate the secure implementation for preventing Broken Object Level Authorization (BOLA) vulnerabilities, adhering to OWASP Top 10 best practices.

## Secure Implementation Logic
- **Control Family:** `AUTHZ` (Identity and Object-Level Authorization).
- **Core Principle:** Every request for a resource must verify that the requesting actor has explicit permission to access that specific resource.
- **Implementation:**
    - The `DemoService` simulates an ownership check where an `actor` (verified identity) is compared against the `resourceOwner` fetched from a trusted data source.
    - Access is denied if the identity doesn't match the owner, preventing horizontal privilege escalation.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoController.java`: API entry point that accepts `actor` and `resourceId` parameters to simulate different access scenarios.
- `src/main/java/.../DemoService.java`: Contains the core authorization logic. It enforces that only the owner can access the resource.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying that owner access is allowed and unauthorized access is correctly denied.
- `src/test/java/.../DemoServiceTest.java`: Unit tests for the authorization logic.
<!-- CODE_MAP_END -->

## Quick Start
1. Build and Test: `mvn clean test`
2. Run locally: `mvn spring-boot:run`
3. Verify via Curl:
    - Success: `curl "http://localhost:8080/api/demo?actor=bob&resourceId=bob_profile"`
    - Deny: `curl "http://localhost:8080/api/demo?actor=alice&resourceId=bob_profile"`

## Acceptance Criteria
- Unauthorized access attempts resulting in a `controlDecision: deny`.
- Authorized access attempts resulting in a `controlDecision: allow`.
- Response payloads include standard metadata (`riskScore`, `controlFamily`, `concept`).
