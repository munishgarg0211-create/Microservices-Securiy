# ch2 - STRIDE Control Mapper

- Book: book-1-foundations-core-security
- Chapter: ch2
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, JUnit 5

## Objective
Demonstrate the systematic mapping of STRIDE (Spoofing, Tampering, Repudiation, Information Disclosure, Denial of Service, Elevation of Privilege) threats to production-grade security controls.

## Secure Implementation Logic
- **Control Family:** `THREAT` (Threat Modeling and Mitigation).
- **Core Principle:** Every potential threat identified during architectural design must be mapped to a verifiable security control to ensure full lifecycle coverage.
- **Implementation:**
    - The `DemoService` provides a mapping engine that correlates STRIDE threats with modern microservice security patterns (e.g., JWT for Spoofing, mTLS for Information Disclosure).
    - It ensures that architectural decisions are driven by recognized threat models rather than ad-hoc security patches.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoController.java`: API entry point that accepts a `threat` parameter to retrieve its corresponding mitigation.
- `src/main/java/.../DemoService.java`: Contains the mapping logic that connects STRIDE categories to architectural security controls.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying correct mapping behavior and handling of unrecognized categories.
- `src/test/java/.../DemoServiceTest.java`: Unit tests for the threat mapping logic.
<!-- CODE_MAP_END -->

## Quick Start
1. Build and Test: `mvn clean test`
2. Run locally: `mvn spring-boot:run`
3. Verify via Curl:
    - Success: `curl "http://localhost:8080/api/demo?threat=Spoofing"`
    - Success: `curl "http://localhost:8080/api/demo?threat=Tampering"`

## Acceptance Criteria
- Recognized STRIDE threats successfully mapping to security controls with `controlDecision: mitigated`.
- Unrecognized inputs resulting in `controlDecision: exposed` and a high risk score.
- Response payloads include standard metadata (`riskScore`, `controlFamily`, `concept`).
