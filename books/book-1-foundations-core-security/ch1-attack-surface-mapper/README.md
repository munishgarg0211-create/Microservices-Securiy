# ch1 - Attack Surface Mapper

- Book: book-1-foundations-core-security
- Chapter: ch1
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, JUnit 5

## Objective
Demonstrate secure attack surface analysis by mapping service entry points and enforcing minimization strategies (e.g., hiding internal APIs, enforcing MFA).

## Secure Implementation Logic
- **Control Family:** `THREAT` (Threat Analysis and Surface Minimization).
- **Core Principle:** A smaller attack surface is easier to defend. Every exposed endpoint must be justified, documented, and protected with modern security controls.
- **Implementation:**
    - The `DemoService` simulates an attack surface mapping engine.
    - It evaluates the ratio of `exposedEndpoints` to `totalEndpoints`.
    - It verifies if `mfaEnforced` is active across public entry points.
    - An optimized surface is one where exposure is minimized and authentication is hardened.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoController.java`: API entry point that accepts topology parameters to simulate attack surface scenarios.
- `src/main/java/.../DemoService.java`: Contains the logic for evaluating and scoring the attack surface.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying the surface optimization logic.
- `src/test/java/.../DemoServiceTest.java`: Unit tests for the mapping and scoring logic.
<!-- CODE_MAP_END -->

## Quick Start
1. Build and Test: `mvn clean test`
2. Run locally: `mvn spring-boot:run`
3. Verify via Curl:
    - Success (Optimized): `curl "http://localhost:8080/api/demo?endpoints=10&exposed=1&mfaEnforced=true"`
    - Success (Over-exposed): `curl "http://localhost:8080/api/demo?endpoints=10&exposed=5&mfaEnforced=false"`

## Acceptance Criteria
- Topologies with low exposure and enforced MFA resulting in `controlDecision: optimized`.
- Topologies with high exposure or missing MFA resulting in `controlDecision: over-exposed`.
- Response payloads include standard metadata (`riskScore`, `controlFamily`, `concept`).
