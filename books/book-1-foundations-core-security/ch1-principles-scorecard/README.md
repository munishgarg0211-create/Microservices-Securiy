# ch1 - Security Principles Scorecard

- Book: book-1-foundations-core-security
- Chapter: ch1
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, JUnit 5

## Objective
Evaluate system architectures against foundational microservice security principles, including Defense-in-Depth, Least Privilege, and Secure Failures.

## Secure Implementation Logic
- **Control Family:** `GOVERNANCE` (Security Principles and Architectural Alignment).
- **Core Principle:** A resilient system is built on a foundation of sound security principles. Every architectural decision should be measured against these principles to ensure consistent and multi-layered protection.
- **Implementation:**
    - The `DemoService` acts as a scorecard engine.
    - It evaluates three core indicators: `defenseInDepth`, `leastPrivilege`, and `failSecurely`.
    - It calculates a `complianceScore` based on the implementation of these principles.
    - An architecture is only marked as `compliant` if all foundational principles are strictly followed.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoController.java`: API entry point that accepts principle implementation flags to evaluate architectural standing.
- `src/main/java/.../DemoService.java`: Contains the scoring logic for the principles scorecard.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying scorecard compliance and risk scoring.
- `src/test/java/.../DemoServiceTest.java`: Unit tests for the evaluation and scoring logic.
<!-- CODE_MAP_END -->

## Quick Start
1. Build and Test: `mvn clean test`
2. Run locally: `mvn spring-boot:run`
3. Verify via Curl:
    - Success (Compliant): `curl "http://localhost:8080/api/demo?defenseInDepth=true&leastPrivilege=true&failSecurely=true"`
    - Success (Non-compliant): `curl "http://localhost:8080/api/demo?defenseInDepth=true&leastPrivilege=false&failSecurely=true"`

## Acceptance Criteria
- Architectures meeting all core principles resulting in `controlDecision: compliant` and a 100% score.
- Architectures failing any single principle resulting in `controlDecision: non-compliant` and a high risk score.
- Response payloads include standard metadata (`riskScore`, `controlFamily`, `concept`).
