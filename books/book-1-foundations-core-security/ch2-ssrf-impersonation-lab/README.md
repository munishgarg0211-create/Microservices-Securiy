# ch2 - SSRF Impersonation Lab

- Book: book-1-foundations-core-security
- Chapter: ch2
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, JUnit 5

## Objective
Demonstrate secure implementation for preventing Server-Side Request Forgery (SSRF) vulnerabilities, specifically protecting internal metadata services and private networks.

## Secure Implementation Logic
- **Control Family:** `THREAT` (Threat Mitigation).
- **Core Principle:** Applications that make outbound requests must strictly validate the target destination against an allow-list or block-list to prevent access to internal-only resources.
- **Implementation:**
    - The `DemoService` validates the target `url` against common internal patterns (`localhost`, `127.0.0.1`, `169.254.169.254`, and the `internal` keyword).
    - Requests targeting these sensitive locations are denied, ensuring the application cannot be leveraged to probe the internal network.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoController.java`: API entry point that accepts a `url` parameter to simulate outbound request targeting.
- `src/main/java/.../DemoService.java`: Contains the core validation logic. It blocks requests to protected internal destinations.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying that requests to public URLs are allowed while internal targets are blocked.
- `src/test/java/.../DemoServiceTest.java`: Unit tests for the SSRF validation logic.
<!-- CODE_MAP_END -->

## Quick Start
1. Build and Test: `mvn clean test`
2. Run locally: `mvn spring-boot:run`
3. Verify via Curl:
    - Success: `curl "http://localhost:8080/api/demo?url=https://api.external.com"`
    - Deny: `curl "http://localhost:8080/api/demo?url=http://169.254.169.254/latest/meta-data/"`

## Acceptance Criteria
- Outbound requests to internal/private IP ranges resulting in a `controlDecision: deny`.
- Outbound requests to public URLs resulting in a `controlDecision: allow`.
- Response payloads include standard metadata (`riskScore`, `controlFamily`, `concept`).
