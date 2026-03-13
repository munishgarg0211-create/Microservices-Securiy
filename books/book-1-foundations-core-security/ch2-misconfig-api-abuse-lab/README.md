# ch2 - Misconfig API Abuse Lab

- Book: book-1-foundations-core-security
- Chapter: ch2
- Status: executable-demo
- Suggested stack: Java 21, Spring Boot 3.x, JUnit 5

## Objective
Demonstrate secure implementation for preventing API abuse and brute-force attacks through restrictive configuration and rate limiting.

## Secure Implementation Logic
- **Control Family:** `THREAT` (Threat Mitigation).
- **Core Principle:** APIs must enforce thresholds on sensitive operations (like login or data export) to prevent automated abuse and brute-force discovery.
- **Implementation:**
    - The `DemoService` simulates a rate-limiting control by tracking `attempts`.
    - If the number of attempts exceeds the security `abuseThreshold` (set to 5), the application transitions from `allow` to `throttle`.
    - This ensures that while legitimate users are served, automated attackers are slowed down or blocked.

## Code Demonstration Map
<!-- CODE_MAP_START -->
- `src/main/java/.../DemoController.java`: API entry point that accepts an `attempts` parameter to simulate request volume.
- `src/main/java/.../DemoService.java`: Contains the core throttling logic. It blocks excessive requests based on the established threshold.
- `src/test/java/.../DemoControllerTest.java`: Integration tests verifying that requests below the threshold are allowed and those above are throttled.
- `src/test/java/.../DemoServiceTest.java`: Unit tests for the API abuse mitigation logic.
<!-- CODE_MAP_END -->

## Quick Start
1. Build and Test: `mvn clean test`
2. Run locally: `mvn spring-boot:run`
3. Verify via Curl:
    - Success: `curl "http://localhost:8080/api/demo?attempts=3"`
    - Throttle: `curl "http://localhost:8080/api/demo?attempts=10"`

## Acceptance Criteria
- Request volume exceeding the threshold resulting in a `controlDecision: throttle`.
- Request volume within the threshold resulting in a `controlDecision: allow`.
- Response payloads include standard metadata (`riskScore`, `controlFamily`, `concept`).
