# ch2 - Supply Chain Mini-Sim

- Book: book-1-foundations-core-security
- Chapter: ch2
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, JUnit 5

## Objective
Demonstrate secure implementation of supply chain gates, ensuring that only verified and signed artifacts with available metadata (SBOM) are allowed to proceed through the delivery pipeline.

## Secure Implementation Logic
- **Control Family:** `POLICY` (Governance and Policy Enforcement).
- **Core Principle:** Every software artifact must be cryptographically signed by a trusted authority and accompanied by a Software Bill of Materials (SBOM) to verify its integrity and provenance.
- **Implementation:**
    - The `DemoService` simulates a deployment gate.
    - It enforces checks for `imageSigned` and `hasSbom`.
    - If either check fails, the deployment is blocked, preventing unvetted code from reaching production.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoController.java`: API entry point that accepts `imageSigned` and `hasSbom` parameters to simulate artifact validation states.
- `src/main/java/.../DemoService.java`: Contains the core policy enforcement logic for supply-chain gates.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying that only signed artifacts with SBOMs are allowed.
- `src/test/java/.../DemoServiceTest.java`: Unit tests for the artifact validation logic.
<!-- CODE_MAP_END -->

## Quick Start
1. Build and Test: `mvn clean test`
2. Run locally: `mvn spring-boot:run`
3. Verify via Curl:
    - Success: `curl "http://localhost:8080/api/demo?imageSigned=true&hasSbom=true"`
    - Deny: `curl "http://localhost:8080/api/demo?imageSigned=false&hasSbom=true"`

## Acceptance Criteria
- Artifacts failing signing or SBOM checks resulting in a `controlDecision: deny`.
- Fully validated artifacts resulting in a `controlDecision: allow`.
- Response payloads include standard metadata (`riskScore`, `controlFamily`, `concept`).
