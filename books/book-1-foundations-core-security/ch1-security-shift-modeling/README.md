# ch1 - Security Shift-Left Modeling

- Book: book-1-foundations-core-security
- Chapter: ch1
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, JUnit 5

## Objective
Demonstrate the effectiveness of "shifting security left" by integrating design reviews and threat modeling early in the microservice lifecycle to minimize residual risk.

## Secure Implementation Logic
- **Control Family:** `THREAT` (Threat Modeling and Design Review).
- **Core Principle:** Architectural Resilience. Identifying and mitigating vulnerabilities during the design phase is significantly more cost-effective than patching them in production. This project demonstrates how systematic modeling can shield a service from architectural flaws before code is even written.
- **Implementation:**
    - The `DemoService` simulates a security maturity evaluation during the design phase.
    - It verifies if a `designReviewDone` and `threatModelingDone` were performed.
    - It tracks an `earlyMitigationRate` representing the percentage of threats identified and addressed before implementation.
    - A service is considered `shielded` only if it has undergone thorough modeling and meets high mitigation thresholds.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoController.java`: API entry point that accepts modeling status and mitigation rates to evaluate shift-left maturity.
- `src/main/java/.../DemoService.java`: Contains the logic for scoring and evaluating the shift-left security effort.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying shielded vs. exposed outcomes based on modeling intensity.
- `src/test/java/.../DemoServiceTest.java`: Unit tests for the shift-left evaluation logic.
<!-- CODE_MAP_END -->

## Quick Start
1. Build and Test: `mvn clean test`
2. Run locally: `mvn spring-boot:run`
3. Verify via Curl:
    - Success (Shielded): `curl "http://localhost:8080/api/demo?designReview=true&threatModeling=true&earlyMitigationRate=90"`
    - Success (Exposed): `curl "http://localhost:8080/api/demo?designReview=true&threatModeling=false&earlyMitigationRate=85"`

## Acceptance Criteria
- Projects with completed design reviews, threat models, and high early mitigation rates resulting in `controlDecision: shielded`.
- Projects missing modeling activities or having low mitigation rates resulting in `controlDecision: exposed` and a high risk score.
- Response payloads include standard metadata (`riskScore`, `controlFamily`, `concept`).
